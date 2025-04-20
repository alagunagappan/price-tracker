package com.pricetracker.service;

import com.pricetracker.model.PriceAlert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {

    public void sendNotification(PriceAlert alert, double currentPrice) {
        // In a real implementation, this would send email/SMS/webhook
        String message = String.format(
                "Price alert! %s is now $%.2f (your target: $%.2f)",
                alert.getProduct().getUrl(),
                currentPrice,
                alert.getTargetPrice()
        );

        log.info("Sending notification to {}: {}", alert.getUserEmail(), message);

    }
}
