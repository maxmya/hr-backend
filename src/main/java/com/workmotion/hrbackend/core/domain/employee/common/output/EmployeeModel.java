package com.workmotion.hrbackend.core.domain.employee.common.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class EmployeeModel {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String age;
    private String state;
}
