package com.pricetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ApiResponse {

    private boolean success;
    private String message;
    private Map<String, Object> data;
}
