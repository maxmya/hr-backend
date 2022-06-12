package com.workmotion.hrbackend.core.domain.employee.common.input;

import com.workmotion.hrbackend.core.domain.employee.EmployeeEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class EmployeeStateChangeRequestModel {
    private Long employeeId;
    private EmployeeEvent employeeEvent;
}
