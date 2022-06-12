package com.workmotion.hrbackend.core.application.employee.statemachine.config;


import com.workmotion.hrbackend.core.domain.employee.EmployeeEvent;
import com.workmotion.hrbackend.core.domain.employee.EmployeeState;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@EnableStateMachineFactory
@Configuration
public class EmployeeStateMachineConfig extends StateMachineConfigurerAdapter<EmployeeState, EmployeeEvent> {

	@Override
	public void configure(StateMachineStateConfigurer<EmployeeState, EmployeeEvent> states) throws Exception {
		states.withStates()
				.initial(EmployeeState.ADDED)
				.states(EnumSet.allOf(EmployeeState.class))
				.end(EmployeeState.ACTIVE);
	}


	@Override
	public void configure(StateMachineTransitionConfigurer<EmployeeState, EmployeeEvent> transitions) throws Exception {
		transitions.withExternal()
				.source(EmployeeState.ADDED).target(EmployeeState.IN_CHECK).event(EmployeeEvent.BEGIN_CHECK)
				.and().withExternal()
				.source(EmployeeState.IN_CHECK).target(EmployeeState.APPROVED).event(EmployeeEvent.APPROVE)
				.and().withExternal()
				.source(EmployeeState.APPROVED).target(EmployeeState.IN_CHECK).event(EmployeeEvent.UNAPPROVE)
				.and().withExternal()
				.source(EmployeeState.APPROVED).target(EmployeeState.ACTIVE).event(EmployeeEvent.ACTIVATE);

	}
}
