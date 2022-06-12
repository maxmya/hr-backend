package com.workmotion.hrbackend.entrypoint.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workmotion.hrbackend.core.domain.employee.EmployeeEvent;
import com.workmotion.hrbackend.core.domain.employee.EmployeeState;
import com.workmotion.hrbackend.entrypoint.employee.advice.response.ErrorResponse;
import com.workmotion.hrbackend.entrypoint.employee.controller.request.EmployeeRegistrationRequest;
import com.workmotion.hrbackend.entrypoint.employee.controller.response.EmployeeDetailsResponse;
import com.workmotion.hrbackend.entrypoint.employee.controller.response.EmployeeRegistrationResponse;
import com.workmotion.hrbackend.entrypoint.employee.controller.response.EmployeeStateChangeResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.text.MessageFormat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest {

    private static final String EMPLOYEE_REGISTER_PATH = "/api/v1/employee/register";
    private static final String EMPLOYEE_DETAILS_PATH = "/api/v1/employee/{0}";
    private static final String EMPLOYEE_STATE_CHANGE_PATH = "/api/v1/employee/{0}/state/{1}";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void registerEmployee_validBody() throws Exception {

        final var employeeRegRequest = EmployeeRegistrationRequest
                .builder()
                .name("Mahmoud")
                .age("10")
                .email("test@test.com")
                .phone("13245")
                .build();

        final var employeeRegResponse = EmployeeRegistrationResponse
                .builder()
                .id(1L)
                .name("Mahmoud")
                .age("10")
                .email("test@test.com")
                .phone("13245")
                .state(EmployeeState.ADDED.toString())
                .build();

        mockMvc.perform(post(EMPLOYEE_REGISTER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeRegRequest))
                ).andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(employeeRegResponse)));

    }

    @Test
    void registerEmployee_invalidBody() throws Exception {

        final var employeeRegRequest = EmployeeRegistrationRequest
                .builder().build();

        final var errorResponse = ErrorResponse
                .builder()
                .message("invalid request body fields")
                .code("400")
                .build();

        mockMvc.perform(post(EMPLOYEE_REGISTER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeRegRequest))
                ).andExpect(status().is4xxClientError())
                .andExpect(content().json(objectMapper.writeValueAsString(errorResponse)));

    }

    @Test
    void getEmployeeDetails_validId() throws Exception {

        final var employeeRegRequest = EmployeeRegistrationRequest
                .builder()
                .name("Mahmoud")
                .age("10")
                .email("test@test.com")
                .phone("13245")
                .build();

        mockMvc.perform(post(EMPLOYEE_REGISTER_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeRegRequest))
        );

        final var employeeDetailsResponse = EmployeeDetailsResponse
                .builder()
                .id(1L)
                .name("Mahmoud")
                .age("10")
                .email("test@test.com")
                .phone("13245")
                .state(EmployeeState.ADDED.toString())
                .build();

        final var url = MessageFormat.format(EMPLOYEE_DETAILS_PATH, 1L);
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(employeeDetailsResponse)));

    }

    @Test
    void getEmployeeDetails_invalidId() throws Exception {

        final var url = MessageFormat.format(EMPLOYEE_DETAILS_PATH, 100L);
        mockMvc.perform(get(url))
                .andExpect(status().isNotFound());

    }

    @Test
    void changeEmployeeState_validState() throws Exception {

        final var employeeRegRequest = EmployeeRegistrationRequest
                .builder()
                .name("Mahmoud")
                .age("10")
                .email("test@test.com")
                .phone("13245")
                .build();

        mockMvc.perform(post(EMPLOYEE_REGISTER_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeRegRequest))
        );

        final var stateChangeResponse = EmployeeStateChangeResponse
                .builder()
                .employeeId(1L)
                .employeeState(EmployeeState.IN_CHECK.toString())
                .build();

        final var url = MessageFormat.format(EMPLOYEE_STATE_CHANGE_PATH, "1", EmployeeEvent.BEGIN_CHECK.toString());
        mockMvc.perform(put(url))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(stateChangeResponse)));

    }

    @Test
    void changeEmployeeState_invalidStateTransition() throws Exception {

        final var employeeRegRequest = EmployeeRegistrationRequest
                .builder()
                .name("Mahmoud")
                .age("10")
                .email("test@test.com")
                .phone("13245")
                .build();

        mockMvc.perform(post(EMPLOYEE_REGISTER_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeRegRequest))
        );

        final var errorResponse = ErrorResponse
                .builder()
                .message("invalid transition from current state")
                .code("400")
                .build();


        final var url = MessageFormat.format(EMPLOYEE_STATE_CHANGE_PATH, "1", EmployeeEvent.ACTIVATE.toString());
        mockMvc.perform(put(url))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(errorResponse)));

    }

    @Test
    void changeEmployeeState_notSupportedTransition() throws Exception {

        final var employeeRegRequest = EmployeeRegistrationRequest
                .builder()
                .name("Mahmoud")
                .age("10")
                .email("test@test.com")
                .phone("13245")
                .build();

        mockMvc.perform(post(EMPLOYEE_REGISTER_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeRegRequest))
        );

        final var errorResponse = ErrorResponse
                .builder()
                .message("not valid argument in request")
                .code("400")
                .build();


        final var url = MessageFormat.format(EMPLOYEE_STATE_CHANGE_PATH, "1", "RANDOM_TRANSITION");
        mockMvc.perform(put(url))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(errorResponse)));

    }

}
