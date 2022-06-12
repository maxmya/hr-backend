package com.workmotion.hrbackend.entrypoint.employee.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class EmployeeRegistrationResponse {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String age;
    private String state;
}
