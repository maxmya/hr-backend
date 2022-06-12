package com.workmotion.hrbackend.core.application.employee.usecase;

import com.workmotion.hrbackend.core.domain.employee.common.input.EmployeeRegistrationRequestModel;
import com.workmotion.hrbackend.core.domain.employee.common.output.EmployeeModel;
import com.workmotion.hrbackend.core.domain.employee.common.output.EmployeeRegistrationResponseModel;
import com.workmotion.hrbackend.core.domain.employee.EmployeeState;

public interface EmployeeUseCase {

    EmployeeRegistrationResponseModel registerEmployee(final EmployeeRegistrationRequestModel employeeRegistrationRequestModel);

    EmployeeState moveEmployeeToCheckIn(final Long employeeId);

    EmployeeState approveEmployee(final Long employeeId);

    EmployeeState unapproveEmployee(final Long employeeId);

    EmployeeState activateEmployee(final Long employeeId);

    EmployeeModel getEmployeeDetails(final Long employeeId);

}
