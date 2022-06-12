package com.workmotion.hrbackend.core.application.employee.statemachine;

import com.workmotion.hrbackend.core.domain.employee.EmployeeEvent;
import com.workmotion.hrbackend.core.domain.employee.EmployeeState;
import org.springframework.statemachine.StateMachine;

public interface EmployeeStateMachine {

    String EMPLOYEE_ID_HEADER = "employee_state_header";

    StateMachine<EmployeeState, EmployeeEvent> build(final Long employeeId);

    void sendEvent(final Long employeeId,
                   final EmployeeEvent event,
                   final StateMachine<EmployeeState, EmployeeEvent> employeeEventStateMachine);

}

