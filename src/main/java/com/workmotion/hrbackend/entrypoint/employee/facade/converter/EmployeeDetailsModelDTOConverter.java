package com.workmotion.hrbackend.entrypoint.employee.facade.converter;

import com.workmotion.hrbackend.core.domain.employee.common.output.EmployeeModel;
import com.workmotion.hrbackend.entrypoint.employee.controller.response.EmployeeDetailsResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class EmployeeDetailsModelDTOConverter implements Converter<EmployeeModel, EmployeeDetailsResponse> {

    @Override
    public @NonNull EmployeeDetailsResponse convert(@NonNull EmployeeModel source) {
        return EmployeeDetailsResponse.builder()
                .id(source.getId())
                .name(source.getName())
                .age(source.getAge())
                .email(source.getEmail())
                .phone(source.getPhone())
                .state(source.getState())
                .build();
    }

}
