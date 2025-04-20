package com.pricetracker.exception;

import com.pricetracker.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidTargetPriceException.class)
    public ResponseEntity<ApiResponse> handleInvalidTargetPriceException(InvalidTargetPriceException ex) {
        ApiResponse response = new ApiResponse(false, ex.getMessage(), Collections.emptyMap());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleAllExceptions(Exception ex) {
        ApiResponse response = new ApiResponse(false, "Something went wrong", Collections.emptyMap());
        return ResponseEntity.internalServerError().body(response);
    }
}
