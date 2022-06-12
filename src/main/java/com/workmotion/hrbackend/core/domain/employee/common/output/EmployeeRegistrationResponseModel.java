package com.workmotion.hrbackend.core.domain.employee.common.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class EmployeeRegistrationResponseModel {
    private Long employeeId;
    private String name;
    private String phone;
    private String email;
    private String age;
    private String employeeState;
}
