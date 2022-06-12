package com.workmotion.hrbackend.infrastructure.database.employee.persistence.impl;

import com.workmotion.hrbackend.infrastructure.database.employee.persistence.EmployeeEntity;
import com.workmotion.hrbackend.infrastructure.database.employee.repository.EmployeeJPARepository;
import com.workmotion.hrbackend.infrastructure.database.employee.persistence.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@AllArgsConstructor
@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final EmployeeJPARepository employeeJPARepository;

    @Override
    public EmployeeEntity upsert(final EmployeeEntity employeeEntity) {
        return employeeJPARepository.save(employeeEntity);
    }

    @Override
    public Optional<EmployeeEntity> getEmployeeDetailsById(final Long id) {
        return employeeJPARepository.findById(id);
    }
}
