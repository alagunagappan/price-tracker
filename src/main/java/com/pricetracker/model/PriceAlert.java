package com.pricetracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceAlert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    private double targetPrice;
    private String userEmail;

    @Enumerated(EnumType.STRING)
    private Frequency checkFrequency = Frequency.DAILY;
    private boolean active = true;
    private boolean triggered = false;

    private LocalDateTime createdAt;
    private LocalDateTime lastChecked;
}
