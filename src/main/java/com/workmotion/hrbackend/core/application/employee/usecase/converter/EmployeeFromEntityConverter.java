package com.workmotion.hrbackend.core.application.employee.usecase.converter;

import com.workmotion.hrbackend.core.domain.employee.common.output.EmployeeModel;
import com.workmotion.hrbackend.infrastructure.database.employee.persistence.EmployeeEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class EmployeeFromEntityConverter implements Converter<EmployeeEntity, EmployeeModel> {

    @Override
    public @NonNull EmployeeModel convert(@NonNull final EmployeeEntity source) {
        return EmployeeModel.builder()
                .id(source.getId())
                .name(source.getName())
                .age(source.getAge())
                .email(source.getEmail())
                .phone(source.getPhone())
                .state(source.getState().toString())
                .build();
    }

}
