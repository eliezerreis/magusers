package com.magmutual.magusers.exceptions;

import com.magmutual.magusers.dto.ErrorResponseDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Centralized class to gracefully handle various errors across the microservices;
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Override the validation exception provided by spring for a more intuitive feedback;
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, status);
    }

    /**
     * Override the common missing path parameter exception provided by spring for a more intuitive feedback;
     */
    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(
        MissingPathVariableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String apiPath = request.getDescription(false);
        String message = String.format("Required path parameter '%s' is missing", ex.getVariableName());

        ErrorResponseDTO error = new ErrorResponseDTO(apiPath, HttpStatus.BAD_REQUEST, message, LocalDateTime.now());

        return new ResponseEntity<>(error, error.getStatus());
    }

    /**
     * Override the common missing request parameter exception provided by spring for a more intuitive feedback;
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
        MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String apiPath = request.getDescription(false);
        String message = String.format("Required request parameter '%s' is missing", ex.getParameterName());

        ErrorResponseDTO error = new ErrorResponseDTO(apiPath, HttpStatus.BAD_REQUEST, message, LocalDateTime.now());

        return new ResponseEntity<>(error, error.getStatus());
    }

    /**
     * This method handles any generic exception that can happen on the application.
     *
     * @param e       the exception
     * @param request the request details
     * @return The structured response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handle(Exception e, WebRequest request) {
        ErrorResponseDTO error = new ErrorResponseDTO(
            request.getDescription(false),
            HttpStatus.INTERNAL_SERVER_ERROR,
            e.getMessage(),
            LocalDateTime.now());

        return new ResponseEntity<>(error, error.getStatus());
    }
}