package com.workmotion.hrbackend.core.domain.employee.common.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class EmployeeStateChangeResponseModel {
    private Long employeeId;
    private String employeeState;
}
