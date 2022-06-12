package com.workmotion.hrbackend.infrastructure.database.employee.repository;

import com.workmotion.hrbackend.infrastructure.database.employee.persistence.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeJPARepository extends JpaRepository<EmployeeEntity, Long> {
}
