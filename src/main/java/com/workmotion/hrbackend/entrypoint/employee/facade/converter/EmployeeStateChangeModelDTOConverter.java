package com.workmotion.hrbackend.entrypoint.employee.facade.converter;

import com.workmotion.hrbackend.core.domain.employee.common.output.EmployeeStateChangeResponseModel;
import com.workmotion.hrbackend.entrypoint.employee.controller.response.EmployeeStateChangeResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class EmployeeStateChangeModelDTOConverter implements Converter<EmployeeStateChangeResponseModel, EmployeeStateChangeResponse> {

    @Override
    public @NonNull EmployeeStateChangeResponse convert(@NonNull final EmployeeStateChangeResponseModel source) {
        return EmployeeStateChangeResponse.builder()
                .employeeId(source.getEmployeeId())
                .employeeState(source.getEmployeeState())
                .build();
    }

}
