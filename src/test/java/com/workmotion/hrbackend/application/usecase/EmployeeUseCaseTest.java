package com.workmotion.hrbackend.application.usecase;

import com.workmotion.hrbackend.core.application.employee.statemachine.EmployeeStateMachine;
import com.workmotion.hrbackend.core.application.employee.usecase.EmployeeUseCase;
import com.workmotion.hrbackend.core.application.employee.usecase.converter.EmployeeFromEntityConverter;
import com.workmotion.hrbackend.core.application.employee.usecase.converter.EmployeeToEntityConverter;
import com.workmotion.hrbackend.core.application.employee.usecase.impl.EmployeeUseCaseImpl;
import com.workmotion.hrbackend.core.domain.employee.EmployeeState;
import com.workmotion.hrbackend.core.domain.employee.common.input.EmployeeRegistrationRequestModel;
import com.workmotion.hrbackend.infrastructure.database.employee.persistence.EmployeeEntity;
import com.workmotion.hrbackend.infrastructure.database.employee.persistence.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {EmployeeUseCaseImpl.class})
public class EmployeeUseCaseTest {

    @Autowired
    private EmployeeUseCase employeeUseCase;
    @MockBean
    private EmployeeRepository employeeRepository;
    @MockBean
    private EmployeeStateMachine employeeStateMachine;
    @MockBean
    private EmployeeToEntityConverter employeeToEntityConverter;
    @MockBean
    private EmployeeFromEntityConverter employeeFromEntityConverter;

    @Test
    void registerEmployee_validRequestModel() {

        final EmployeeEntity employeeEntity = EmployeeEntity
                .builder()
                .id(1L)
                .name("Mahmoud")
                .email("mahmoud@test.com")
                .phone("010333333")
                .age("20")
                .state(EmployeeState.ADDED)
                .build();

        when(employeeRepository.upsert(any())).thenReturn(employeeEntity);
        when(employeeToEntityConverter.convert(any())).thenReturn(employeeEntity);

        final EmployeeRegistrationRequestModel requestModel = EmployeeRegistrationRequestModel
                .builder()
                .name("Mahmoud")
                .email("mahmoud@test.com")
                .phone("010333333")
                .age("20")
                .build();


        final var responseModel = employeeUseCase.registerEmployee(requestModel);

        assertAll(
                () -> assertEquals(employeeEntity.getId(), responseModel.getEmployeeId()),
                () -> assertEquals(employeeEntity.getName(), responseModel.getName()),
                () -> assertEquals(employeeEntity.getAge(), responseModel.getAge()),
                () -> assertEquals(employeeEntity.getEmail(), responseModel.getEmail()),
                () -> assertEquals(employeeEntity.getPhone(), responseModel.getPhone()),
                () -> assertEquals(employeeEntity.getState().toString(), responseModel.getEmployeeState())
        );

    }

}
