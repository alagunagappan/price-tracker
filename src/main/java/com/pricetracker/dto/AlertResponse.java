package com.pricetracker.dto;

import com.pricetracker.model.Frequency;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AlertResponse {
    private Long id;
    private String productUrl;
    private String productName;
    private double targetPrice;
    private double currentPrice;
    private String userEmail;
    private Frequency checkFrequency;
    private boolean active;
    private boolean triggered;
    private LocalDateTime createdAt;
    private LocalDateTime lastChecked;
}
