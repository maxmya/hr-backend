package com.workmotion.hrbackend.entrypoint.employee.advice;

import com.workmotion.hrbackend.core.application.common.exception.EventNotSupportedException;
import com.workmotion.hrbackend.core.application.common.exception.InvalidTransitionException;
import com.workmotion.hrbackend.entrypoint.employee.advice.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class EmployeeControllerAdvice {

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(final EntityNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.builder()
                        .message(exception.getMessage())
                        .code(String.valueOf(HttpStatus.NOT_FOUND.value()))
                        .build());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EventNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleEventNotSupportedException(final EventNotSupportedException exception) {
        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.builder()
                        .message(exception.getMessage())
                        .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .build());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handeMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.builder()
                        .message("invalid request body fields")
                        .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .build());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidTransitionException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTransitionException(final InvalidTransitionException exception) {
        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.builder()
                        .message(exception.getMessage())
                        .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .build());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodTypeMismatchException(final MethodArgumentTypeMismatchException exception) {
        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.builder()
                        .message("not valid argument in request")
                        .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .build());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInternalServerError(final Exception exception) {
        return ResponseEntity
                .internalServerError()
                .body(ErrorResponse.builder()
                        .message(exception.getMessage())
                        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .build());
    }

}
