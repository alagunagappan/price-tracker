package com.pricetracker.controller;

import com.pricetracker.dto.AlertResponse;
import com.pricetracker.dto.ApiResponse;
import com.pricetracker.dto.CreateAlertRequest;
import com.pricetracker.service.AlertService;
import com.pricetracker.util.Constants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(Constants.PATH_API_ALERTS)
public class AlertController {

    @Autowired
    private AlertService alertService;

    @PostMapping
    public ResponseEntity<ApiResponse> createAlert(@Valid @RequestBody CreateAlertRequest request) {
        AlertResponse response = alertService.createAlert(request);
        Map<String, Object> data = new HashMap<>();
        data.put(Constants.EMAIL, response.getUserEmail());
        data.put(Constants.PRODUCT_URL, response.getProductUrl());
        data.put(Constants.PRODUCT, response.getProductName());
        data.put(Constants.TARGET_PRICE, response.getTargetPrice());
        data.put(Constants.NOTIFICATION_FREQUENCY, response.getCheckFrequency());
        ApiResponse apiResponse = new ApiResponse(true, Constants.PRICE_ALERT_CREATED_SUCCESSFULLY, data);
        return ResponseEntity.ok(apiResponse);
    }

}
