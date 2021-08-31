package com.command.aggregator.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CommandExceptionHandler extends ResponseEntityExceptionHandler {

    Logger logger = LoggerFactory.getLogger(CommandExceptionHandler.class);

    @ExceptionHandler(InvalidStateNameException.class)
    protected ResponseEntity<ApiError> handleInvalidStateNameException(
            InvalidStateNameException ex) {
        logger.error("Invalid Input Exception: ",ex.getMessage());
        ApiError error = new ApiError(ex.getMessage(), System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiError> defaultExceptionHandler(Exception ex){
        logger.error("Internal Server Error: ",ex.getMessage());
        ApiError error = new ApiError(ex.getMessage(), System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

}
