package com.workmotion.hrbackend.entrypoint.employee.advice.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@Builder
@AllArgsConstructor
@Getter
public class ErrorResponse {
    private String message;
    private String code;
}
