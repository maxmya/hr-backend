package com.workmotion.hrbackend.entrypoint.employee.controller.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class EmployeeStateChangeResponse {
    private Long employeeId;
    private String employeeState;
}
