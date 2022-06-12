package com.workmotion.hrbackend.entrypoint.employee.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
@Builder
@AllArgsConstructor
public class EmployeeRegistrationRequest {

    @NotEmpty
    private String name;

    @NotEmpty
    private String phone;

    @NotEmpty
    private String email;

    @NotEmpty
    private String age;
}
