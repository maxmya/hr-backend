package com.workmotion.hrbackend.entrypoint.employee.facade.converter;

import com.workmotion.hrbackend.core.domain.employee.common.output.EmployeeRegistrationResponseModel;
import com.workmotion.hrbackend.entrypoint.employee.controller.response.EmployeeRegistrationResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class EmployeeCreationModelDTOConverter implements Converter<EmployeeRegistrationResponseModel, EmployeeRegistrationResponse> {

    @Override
    public @NonNull EmployeeRegistrationResponse convert(@NonNull final EmployeeRegistrationResponseModel source) {
        return EmployeeRegistrationResponse.builder()
                .id(source.getEmployeeId())
                .name(source.getName())
                .age(source.getAge())
                .email(source.getEmail())
                .phone(source.getPhone())
                .state(source.getEmployeeState())
                .build();
    }

}
