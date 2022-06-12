package com.workmotion.hrbackend.core.application.employee.service.impl;

import com.workmotion.hrbackend.core.application.common.exception.EventNotSupportedException;
import com.workmotion.hrbackend.core.domain.employee.common.input.EmployeeRegistrationRequestModel;
import com.workmotion.hrbackend.core.domain.employee.common.output.EmployeeModel;
import com.workmotion.hrbackend.core.domain.employee.common.output.EmployeeRegistrationResponseModel;
import com.workmotion.hrbackend.core.application.employee.service.EmployeeService;
import com.workmotion.hrbackend.core.domain.employee.common.output.EmployeeStateChangeResponseModel;
import com.workmotion.hrbackend.core.application.employee.usecase.EmployeeUseCase;
import com.workmotion.hrbackend.core.domain.employee.EmployeeEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeUseCase employeeUseCase;

    @Override
    public EmployeeRegistrationResponseModel registerEmployee(final EmployeeRegistrationRequestModel employeeRegistrationRequestModel) {
        return employeeUseCase.registerEmployee(employeeRegistrationRequestModel);
    }

    @Override
    public EmployeeStateChangeResponseModel changeEmployeeState(Long employeeId, EmployeeEvent employeeEvent) {
        switch (employeeEvent) {
            case BEGIN_CHECK: {
                var changedToState = employeeUseCase.moveEmployeeToCheckIn(employeeId);
                return EmployeeStateChangeResponseModel.builder()
                        .employeeId(employeeId)
                        .employeeState(changedToState.toString())
                        .build();
            }
            case APPROVE: {
                var changedToState = employeeUseCase.approveEmployee(employeeId);
                return EmployeeStateChangeResponseModel.builder()
                        .employeeId(employeeId)
                        .employeeState(changedToState.toString())
                        .build();
            }
            case UNAPPROVE: {
                var changedToState = employeeUseCase.unapproveEmployee(employeeId);
                return EmployeeStateChangeResponseModel.builder()
                        .employeeId(employeeId)
                        .employeeState(changedToState.toString())
                        .build();
            }
            case ACTIVATE: {
                var changedToState = employeeUseCase.activateEmployee(employeeId);
                return EmployeeStateChangeResponseModel.builder()
                        .employeeId(employeeId)
                        .employeeState(changedToState.toString())
                        .build();
            }
            default:
                throw new EventNotSupportedException("Event ".concat(employeeEvent.toString())
                        .concat(" is not supported"));

        }
    }

    @Override
    public EmployeeModel getEmployeeDetails(Long employeeId) {
        return employeeUseCase.getEmployeeDetails(employeeId);
    }
}
