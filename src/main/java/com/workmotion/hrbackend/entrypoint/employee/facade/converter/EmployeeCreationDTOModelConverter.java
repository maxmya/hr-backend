package com.workmotion.hrbackend.entrypoint.employee.facade.converter;

import com.workmotion.hrbackend.core.domain.employee.common.input.EmployeeRegistrationRequestModel;
import com.workmotion.hrbackend.entrypoint.employee.controller.request.EmployeeRegistrationRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class EmployeeCreationDTOModelConverter implements Converter<EmployeeRegistrationRequest, EmployeeRegistrationRequestModel> {

    @Override
    public @NonNull EmployeeRegistrationRequestModel convert(@NonNull final EmployeeRegistrationRequest source) {
        return EmployeeRegistrationRequestModel.builder()
                .name(source.getName())
                .age(source.getAge())
                .email(source.getEmail())
                .phone(source.getPhone())
                .build();
    }

}
