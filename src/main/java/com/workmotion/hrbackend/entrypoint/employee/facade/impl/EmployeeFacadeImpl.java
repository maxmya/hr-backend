package com.workmotion.hrbackend.entrypoint.employee.facade.impl;

import com.workmotion.hrbackend.core.application.employee.service.EmployeeService;
import com.workmotion.hrbackend.core.domain.employee.EmployeeEvent;
import com.workmotion.hrbackend.entrypoint.employee.controller.request.EmployeeRegistrationRequest;
import com.workmotion.hrbackend.entrypoint.employee.controller.response.EmployeeDetailsResponse;
import com.workmotion.hrbackend.entrypoint.employee.controller.response.EmployeeRegistrationResponse;
import com.workmotion.hrbackend.entrypoint.employee.controller.response.EmployeeStateChangeResponse;
import com.workmotion.hrbackend.entrypoint.employee.facade.EmployeeFacade;
import com.workmotion.hrbackend.entrypoint.employee.facade.converter.EmployeeCreationDTOModelConverter;
import com.workmotion.hrbackend.entrypoint.employee.facade.converter.EmployeeCreationModelDTOConverter;
import com.workmotion.hrbackend.entrypoint.employee.facade.converter.EmployeeDetailsModelDTOConverter;
import com.workmotion.hrbackend.entrypoint.employee.facade.converter.EmployeeStateChangeModelDTOConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class EmployeeFacadeImpl implements EmployeeFacade {

    private final EmployeeCreationDTOModelConverter employeeCreationDTOModelConverter;
    private final EmployeeCreationModelDTOConverter employeeCreationModelDTOConverter;
    private final EmployeeStateChangeModelDTOConverter employeeStateChangeModelDTOConverter;
    private final EmployeeDetailsModelDTOConverter employeeDetailsModelDTOConverter;
    private final EmployeeService employeeService;

    @Override
    public EmployeeRegistrationResponse registerEmployee(final EmployeeRegistrationRequest employeeRegistrationRequest) {
        final var employeeRequestModel = employeeCreationDTOModelConverter.convert(employeeRegistrationRequest);
        final var serviceResponse = employeeService.registerEmployee(employeeRequestModel);
        return employeeCreationModelDTOConverter.convert(serviceResponse);
    }

    @Override
    public EmployeeStateChangeResponse changeEmployeeState(final Long employeeId, EmployeeEvent event) {
        final var serviceResponse = employeeService.changeEmployeeState(employeeId, event);
        return employeeStateChangeModelDTOConverter.convert(serviceResponse);
    }

    @Override
    public EmployeeDetailsResponse getEmployeeDetails(Long employeeId) {
        final var employeeDetails = employeeService.getEmployeeDetails(employeeId);
        return employeeDetailsModelDTOConverter.convert(employeeDetails);
    }
}
