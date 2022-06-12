package com.workmotion.hrbackend.application.statemachine;

import com.workmotion.hrbackend.core.domain.employee.EmployeeEvent;
import com.workmotion.hrbackend.core.domain.employee.EmployeeState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

@ActiveProfiles("test")
@SpringBootTest
public class StateMachineConfigTest {

    @Autowired
    StateMachineFactory<EmployeeState, EmployeeEvent> smFactory;

    @Test
    void testInitialStateValue() {
        final StateMachine<EmployeeState, EmployeeEvent> sm = smFactory.getStateMachine(UUID.randomUUID());
        sm.start();
        Assertions.assertEquals(EmployeeState.ADDED, sm.getState().getId());
    }

    @Test
    void testToCheckInTransition() {
        final StateMachine<EmployeeState, EmployeeEvent> sm = smFactory.getStateMachine(UUID.randomUUID());
        sm.start();
        sm.sendEvent(EmployeeEvent.BEGIN_CHECK);
        Assertions.assertEquals(EmployeeState.IN_CHECK, sm.getState().getId());
    }

    @Test
    void testApproveTransition() {
        final StateMachine<EmployeeState, EmployeeEvent> sm = smFactory.getStateMachine(UUID.randomUUID());
        sm.start();
        sm.sendEvent(EmployeeEvent.BEGIN_CHECK);
        sm.sendEvent(EmployeeEvent.APPROVE);
        Assertions.assertEquals(EmployeeState.APPROVED, sm.getState().getId());
    }


    @Test
    void testUnapproveTransition() {
        final StateMachine<EmployeeState, EmployeeEvent> sm = smFactory.getStateMachine(UUID.randomUUID());
        sm.start();
        sm.sendEvent(EmployeeEvent.BEGIN_CHECK);
        sm.sendEvent(EmployeeEvent.APPROVE);
        sm.sendEvent(EmployeeEvent.UNAPPROVE);
        Assertions.assertEquals(EmployeeState.IN_CHECK, sm.getState().getId());
    }


    @Test
    void testActivateTransition() {
        final StateMachine<EmployeeState, EmployeeEvent> sm = smFactory.getStateMachine(UUID.randomUUID());
        sm.start();
        sm.sendEvent(EmployeeEvent.BEGIN_CHECK);
        sm.sendEvent(EmployeeEvent.APPROVE);
        sm.sendEvent(EmployeeEvent.ACTIVATE);
        Assertions.assertEquals(EmployeeState.ACTIVE, sm.getState().getId());
    }

}
