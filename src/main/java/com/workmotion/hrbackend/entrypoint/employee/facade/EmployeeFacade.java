package com.workmotion.hrbackend.entrypoint.employee.facade;

import com.workmotion.hrbackend.core.domain.employee.EmployeeEvent;
import com.workmotion.hrbackend.entrypoint.employee.controller.request.EmployeeRegistrationRequest;
import com.workmotion.hrbackend.entrypoint.employee.controller.response.EmployeeDetailsResponse;
import com.workmotion.hrbackend.entrypoint.employee.controller.response.EmployeeRegistrationResponse;
import com.workmotion.hrbackend.entrypoint.employee.controller.response.EmployeeStateChangeResponse;

public interface EmployeeFacade {
    EmployeeRegistrationResponse registerEmployee(final EmployeeRegistrationRequest employeeRegistrationRequest);

    EmployeeStateChangeResponse changeEmployeeState(final Long employeeId, final EmployeeEvent state);

    EmployeeDetailsResponse getEmployeeDetails(final Long employeeId);
}
