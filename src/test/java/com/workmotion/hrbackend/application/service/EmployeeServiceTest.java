package com.workmotion.hrbackend.application.service;

import com.workmotion.hrbackend.core.application.employee.service.EmployeeService;
import com.workmotion.hrbackend.core.application.employee.service.impl.EmployeeServiceImpl;
import com.workmotion.hrbackend.core.application.employee.usecase.EmployeeUseCase;
import com.workmotion.hrbackend.core.domain.employee.EmployeeEvent;
import com.workmotion.hrbackend.core.domain.employee.EmployeeState;
import com.workmotion.hrbackend.core.domain.employee.common.input.EmployeeRegistrationRequestModel;
import com.workmotion.hrbackend.core.domain.employee.common.output.EmployeeRegistrationResponseModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

;

@SpringBootTest(classes = {EmployeeServiceImpl.class})
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;
    @MockBean
    private EmployeeUseCase employeeUseCase;

    @Test
    void employeeCreation_ValidBody() {

        final var employeeRegRequestModel = EmployeeRegistrationRequestModel
                .builder()
                .name("Mahmoud")
                .email("test@test.com")
                .phone("135135")
                .age("35")
                .build();


        final var useCaseResponse = EmployeeRegistrationResponseModel
                .builder()
                .employeeId(1L)
                .name("Mahmoud")
                .email("test@test.com")
                .phone("135135")
                .age("35")
                .employeeState("ADDED")
                .build();

        when(employeeUseCase.registerEmployee(employeeRegRequestModel))
                .thenReturn(useCaseResponse);

        final var employeeRegResponseModel = employeeService.registerEmployee(employeeRegRequestModel);


        assertAll(
                () -> assertEquals(employeeRegRequestModel.getName(), employeeRegResponseModel.getName()),
                () -> assertEquals(employeeRegRequestModel.getEmail(), employeeRegResponseModel.getEmail()),
                () -> assertEquals(employeeRegRequestModel.getPhone(), employeeRegResponseModel.getPhone()),
                () -> assertEquals(employeeRegRequestModel.getAge(), employeeRegResponseModel.getAge())
        );

    }

    @Test
    void employeeChangeState_validEvent() {

        when(employeeUseCase.moveEmployeeToCheckIn(1L)).thenReturn(EmployeeState.IN_CHECK);
        when(employeeUseCase.approveEmployee(1L)).thenReturn(EmployeeState.APPROVED);
        when(employeeUseCase.unapproveEmployee(1L)).thenReturn(EmployeeState.IN_CHECK);
        when(employeeUseCase.activateEmployee(1L)).thenReturn(EmployeeState.ACTIVE);

        final var changeToBeginCheck = employeeService.changeEmployeeState(1L, EmployeeEvent.BEGIN_CHECK);
        assertEquals(EmployeeState.IN_CHECK.toString(), changeToBeginCheck.getEmployeeState());
        assertEquals(1L, changeToBeginCheck.getEmployeeId());

        final var changeToApprove = employeeService.changeEmployeeState(1L, EmployeeEvent.APPROVE);
        assertEquals(EmployeeState.APPROVED.toString(), changeToApprove.getEmployeeState());
        assertEquals(1L, changeToApprove.getEmployeeId());

        final var changeToUnApprove = employeeService.changeEmployeeState(1L, EmployeeEvent.UNAPPROVE);
        assertEquals(EmployeeState.IN_CHECK.toString(), changeToUnApprove.getEmployeeState());
        assertEquals(1L, changeToUnApprove.getEmployeeId());

        final var changeToActivate = employeeService.changeEmployeeState(1L, EmployeeEvent.ACTIVATE);
        assertEquals(EmployeeState.ACTIVE.toString(), changeToActivate.getEmployeeState());
        assertEquals(1L, changeToActivate.getEmployeeId());

    }


}
