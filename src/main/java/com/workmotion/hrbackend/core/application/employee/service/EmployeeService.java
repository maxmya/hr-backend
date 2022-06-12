package com.workmotion.hrbackend.core.application.employee.service;

import com.workmotion.hrbackend.core.domain.employee.common.input.EmployeeRegistrationRequestModel;
import com.workmotion.hrbackend.core.domain.employee.common.output.EmployeeModel;
import com.workmotion.hrbackend.core.domain.employee.common.output.EmployeeRegistrationResponseModel;
import com.workmotion.hrbackend.core.domain.employee.common.output.EmployeeStateChangeResponseModel;
import com.workmotion.hrbackend.core.domain.employee.EmployeeEvent;

public interface EmployeeService {

    EmployeeRegistrationResponseModel registerEmployee(final EmployeeRegistrationRequestModel employeeRegistrationRequestModel);

    EmployeeStateChangeResponseModel changeEmployeeState(final Long employeeId, final EmployeeEvent employeeEvent);

    EmployeeModel getEmployeeDetails(final Long employeeId);

}
