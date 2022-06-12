package com.workmotion.hrbackend.core.application.employee.usecase.impl;

import com.workmotion.hrbackend.core.application.annotation.UseCase;
import com.workmotion.hrbackend.core.application.employee.usecase.converter.EmployeeFromEntityConverter;
import com.workmotion.hrbackend.core.domain.employee.common.input.EmployeeRegistrationRequestModel;
import com.workmotion.hrbackend.core.domain.employee.common.output.EmployeeModel;
import com.workmotion.hrbackend.core.domain.employee.common.output.EmployeeRegistrationResponseModel;
import com.workmotion.hrbackend.core.application.employee.statemachine.EmployeeStateMachine;
import com.workmotion.hrbackend.core.application.employee.usecase.EmployeeUseCase;
import com.workmotion.hrbackend.core.application.employee.usecase.converter.EmployeeToEntityConverter;
import com.workmotion.hrbackend.core.domain.employee.EmployeeEvent;
import com.workmotion.hrbackend.core.domain.employee.EmployeeState;
import com.workmotion.hrbackend.infrastructure.database.employee.persistence.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@AllArgsConstructor
@UseCase
public class EmployeeUseCaseImpl implements EmployeeUseCase {

    private final EmployeeRepository employeeRepository;
    private final EmployeeStateMachine employeeStateMachine;
    private final EmployeeToEntityConverter employeeToEntityConverter;
    private final EmployeeFromEntityConverter employeeFromEntityConverter;

    @Override
    public EmployeeRegistrationResponseModel registerEmployee(final EmployeeRegistrationRequestModel employeeRegistrationRequestModel) {
        final var employee = employeeToEntityConverter.convert(employeeRegistrationRequestModel);
        employee.setState(EmployeeState.ADDED);
        final var savedEmployee = employeeRepository.upsert(employee);
        return EmployeeRegistrationResponseModel.builder()
                .employeeId(savedEmployee.getId())
                .name(employee.getName())
                .age(employee.getAge())
                .phone(employee.getPhone())
                .email(employee.getEmail())
                .employeeState(savedEmployee.getState().toString())
                .build();
    }

    @Override
    public EmployeeState moveEmployeeToCheckIn(final Long employeeId) {
        return sendEventToStateMachine(employeeId, EmployeeEvent.BEGIN_CHECK);
    }

    @Override
    public EmployeeState approveEmployee(final Long employeeId) {
        return sendEventToStateMachine(employeeId, EmployeeEvent.APPROVE);
    }

    @Override
    public EmployeeState unapproveEmployee(final Long employeeId) {
        return sendEventToStateMachine(employeeId, EmployeeEvent.UNAPPROVE);
    }

    @Override
    public EmployeeState activateEmployee(final Long employeeId) {
        return sendEventToStateMachine(employeeId, EmployeeEvent.ACTIVATE);
    }

    @Override
    public EmployeeModel getEmployeeDetails(Long employeeId) {
        final var employee = employeeRepository.getEmployeeDetailsById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee with id ".concat(String.valueOf(employeeId))
                        .concat(" is not found")));
        return employeeFromEntityConverter.convert(employee);
    }

    @Transactional
    public EmployeeState sendEventToStateMachine(final Long employeeId, final EmployeeEvent event) {
        final var sm = employeeStateMachine.build(employeeId);
        employeeStateMachine.sendEvent(employeeId, event, sm);
        final var employee = employeeRepository.getEmployeeDetailsById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee with id ".concat(String.valueOf(employeeId))
                        .concat(" is not found")));
        return employee.getState();
    }
}
