package com.pricetracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pricetracker.model.Frequency;
import lombok.Data;

@Data
public class CreateAlertRequest {
    private String productUrl;

    @JsonProperty("price")
    private double targetPrice;

    @JsonProperty("email")
    private String userEmail;

    @JsonProperty("frequency")
    private Frequency checkFrequency;
}