package com.workmotion.hrbackend.core.domain.employee.common.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class EmployeeRegistrationRequestModel {
    private String name;
    private String phone;
    private String email;
    private String age;
}
