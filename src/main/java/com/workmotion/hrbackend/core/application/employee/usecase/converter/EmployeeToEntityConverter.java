package com.workmotion.hrbackend.core.application.employee.usecase.converter;

import com.workmotion.hrbackend.core.domain.employee.common.input.EmployeeRegistrationRequestModel;
import com.workmotion.hrbackend.infrastructure.database.employee.persistence.EmployeeEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class EmployeeToEntityConverter implements Converter<EmployeeRegistrationRequestModel, EmployeeEntity> {

    @Override
    public @NonNull EmployeeEntity convert(@NonNull final EmployeeRegistrationRequestModel source) {
        return EmployeeEntity
                .builder()
                .name(source.getName())
                .age(source.getAge())
                .email(source.getEmail())
                .phone(source.getPhone())
                .build();
    }

}
