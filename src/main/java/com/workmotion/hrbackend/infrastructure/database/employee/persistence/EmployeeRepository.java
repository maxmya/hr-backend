package com.workmotion.hrbackend.infrastructure.database.employee.persistence;

import java.util.Optional;

public interface EmployeeRepository {

    EmployeeEntity upsert(final EmployeeEntity employeeEntity);

    Optional<EmployeeEntity> getEmployeeDetailsById(final Long id);

}
