package com.workmotion.hrbackend.core.application.employee.statemachine.impl;

import com.workmotion.hrbackend.core.application.common.exception.InvalidTransitionException;
import com.workmotion.hrbackend.core.application.employee.statemachine.EmployeeStateChangeInterceptor;
import com.workmotion.hrbackend.core.application.employee.statemachine.EmployeeStateMachine;
import com.workmotion.hrbackend.infrastructure.database.employee.persistence.EmployeeEntity;
import com.workmotion.hrbackend.core.domain.employee.EmployeeEvent;
import com.workmotion.hrbackend.core.domain.employee.EmployeeState;
import com.workmotion.hrbackend.infrastructure.database.employee.persistence.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class EmployeeStateMachineImpl implements EmployeeStateMachine {

    private final StateMachineFactory<EmployeeState, EmployeeEvent> employeeStateMachineFactory;
    private final EmployeeStateChangeInterceptor employeeStateChangeInterceptor;
    private final EmployeeRepository employeeRepository;

    public StateMachine<EmployeeState, EmployeeEvent> build(final Long employeeId) {

        final Optional<EmployeeEntity> employeeOptional = employeeRepository.getEmployeeDetailsById(employeeId);
        final StateMachine<EmployeeState, EmployeeEvent> employeeEventStateMachine =
                employeeStateMachineFactory.getStateMachine(Long.toString(employeeId));

        employeeOptional.ifPresentOrElse(employee -> {
            employeeEventStateMachine.stop();
            employeeEventStateMachine.getStateMachineAccessor()
                    .doWithAllRegions(stateMachineAccessor -> {
                        stateMachineAccessor.addStateMachineInterceptor(employeeStateChangeInterceptor);
                        stateMachineAccessor.resetStateMachine(
                                new DefaultStateMachineContext<>(employee.getState(),
                                        null, null, null)
                        );
                    });
            employeeEventStateMachine.start();
        }, () -> {
            throw new EntityNotFoundException("Employee with id ".concat(Long.toString(employeeId))
                    .concat("is not found"));
        });

        return employeeEventStateMachine;
    }

    public void sendEvent(final Long employeeId,
                          final EmployeeEvent event,
                          final StateMachine<EmployeeState, EmployeeEvent> employeeEventStateMachine) {

        final Message<EmployeeEvent> eventMessage = MessageBuilder.withPayload(event)
                .setHeader(EMPLOYEE_ID_HEADER, employeeId)
                .build();

        final var isEventAccepted = employeeEventStateMachine.sendEvent(eventMessage);

        if (!isEventAccepted) throw new InvalidTransitionException("invalid transition from current state");
    }

}

