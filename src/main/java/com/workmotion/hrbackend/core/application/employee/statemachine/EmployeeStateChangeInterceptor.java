package com.workmotion.hrbackend.core.application.employee.statemachine;


import com.workmotion.hrbackend.core.domain.employee.EmployeeEvent;
import com.workmotion.hrbackend.core.domain.employee.EmployeeState;
import com.workmotion.hrbackend.infrastructure.database.employee.persistence.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@AllArgsConstructor
@Component
public class EmployeeStateChangeInterceptor extends StateMachineInterceptorAdapter<EmployeeState, EmployeeEvent> {

    private final EmployeeRepository employeeRepository;

    @Override
    public void preStateChange(final State<EmployeeState, EmployeeEvent> state,
                               final Message<EmployeeEvent> message,
                               final Transition<EmployeeState, EmployeeEvent> transition,
                               final StateMachine<EmployeeState, EmployeeEvent> stateMachine) {

        Optional.ofNullable(message).flatMap(
                        employeeEventMessage -> Optional.ofNullable(employeeEventMessage.getHeaders()
                                .get(EmployeeStateMachine.EMPLOYEE_ID_HEADER, Long.class)))
                .ifPresent(employeeId -> {
                    final var employee = employeeRepository.getEmployeeDetailsById(employeeId)
                            .orElseThrow(() -> new EntityNotFoundException("Employee With Id "
                                    .concat(String.valueOf(employeeId)).concat(" is not found")));
                    employee.setState(state.getId());
                    employeeRepository.upsert(employee);
                });

    }
}
