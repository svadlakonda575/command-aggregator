package com.command.aggregator.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@AllArgsConstructor
public class ApiError {
    String message;
    Long timestamp;
    HttpStatus statusCode;
}
