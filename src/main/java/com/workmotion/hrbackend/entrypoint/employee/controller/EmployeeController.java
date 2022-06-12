package com.workmotion.hrbackend.entrypoint.employee.controller;

import com.workmotion.hrbackend.core.domain.employee.EmployeeEvent;
import com.workmotion.hrbackend.entrypoint.employee.controller.request.EmployeeRegistrationRequest;
import com.workmotion.hrbackend.entrypoint.employee.controller.response.EmployeeDetailsResponse;
import com.workmotion.hrbackend.entrypoint.employee.controller.response.EmployeeRegistrationResponse;
import com.workmotion.hrbackend.entrypoint.employee.controller.response.EmployeeStateChangeResponse;
import com.workmotion.hrbackend.entrypoint.employee.facade.EmployeeFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(EmployeeController.ROOT_PATH)
@AllArgsConstructor
@CrossOrigin(exposedHeaders = HttpHeaders.LOCATION)
public class EmployeeController {

    public static final String ROOT_PATH = "/api/v1/employee";
    public static final String REGISTER_EMPLOYEE = "/register";
    public static final String EMPLOYEE_STATE_CHANGE_PATH = "/{id}/state/{event}";
    public static final String EMPLOYEE_ID_DETAILS_PATH = "/{id}";
    private final EmployeeFacade employeeFacade;

    @PostMapping(path = REGISTER_EMPLOYEE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EmployeeRegistrationResponse> registerEmployee
            (final @RequestBody @Valid EmployeeRegistrationRequest employeeRegistrationRequest) {

        return ResponseEntity.ok(employeeFacade.registerEmployee(employeeRegistrationRequest));
    }

    @PutMapping(path = EMPLOYEE_STATE_CHANGE_PATH)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EmployeeStateChangeResponse> changeEmployeeState
            (@PathVariable(name = "id") final Long employeeId, @PathVariable final EmployeeEvent event) {

        return ResponseEntity.ok(employeeFacade.changeEmployeeState(employeeId, event));
    }

    @GetMapping(path = EMPLOYEE_ID_DETAILS_PATH)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EmployeeDetailsResponse> getEmployeeDetails(@PathVariable(name = "id") final Long employeeId) {

        return ResponseEntity.ok(employeeFacade.getEmployeeDetails(employeeId));
    }
}
