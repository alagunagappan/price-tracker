package com.pricetracker.service;

import com.pricetracker.dto.AlertResponse;
import com.pricetracker.dto.CreateAlertRequest;
import com.pricetracker.exception.InvalidTargetPriceException;
import com.pricetracker.model.Frequency;
import com.pricetracker.model.PriceAlert;
import com.pricetracker.model.Product;
import com.pricetracker.repository.PriceAlertRepository;
import com.pricetracker.repository.ProductRepository;
import com.pricetracker.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AlertService {

    @Autowired
    private PriceAlertRepository alertRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private NotificationService notificationService;

    public AlertResponse createAlert(CreateAlertRequest request) {
        if(request.getTargetPrice() < 0)
            throw new InvalidTargetPriceException(Constants.MSG_INVALID_TARGET_PRICE);

        // Check if product exists or create a new one
        Product product = productRepository.findByUrl(request.getProductUrl())
                .orElseGet(() -> {
                    Product newProduct = new Product();
                    newProduct.setUrl(request.getProductUrl());
                    // Fetch additional product info
                    productService.getProductByUrl(request.getProductUrl())
                            .ifPresent(p -> {
                                newProduct.setName(p.getName());
                                newProduct.setCurrentPrice(p.getCurrentPrice());
                            });
                    return productRepository.save(newProduct);
                });

        PriceAlert alert = PriceAlert.builder()
                .product(product)
                .targetPrice(request.getTargetPrice())
                .userEmail(request.getUserEmail())
                .checkFrequency(request.getCheckFrequency())
                .createdAt(LocalDateTime.now())
                .active(true)
                .build();

        PriceAlert savedAlert = alertRepository.save(alert);
        log.info("Price alert created successfully for user: {}, Product: {} with target price of {}", request.getUserEmail(),
                product.getUrl(), request.getTargetPrice());
        return convertToResponse(savedAlert);
    }

    public void checkAndTriggerAlerts(Frequency frequency) {
        List<PriceAlert> activeAlerts = alertRepository.findActiveAlertsByFrequency(frequency);

        activeAlerts.forEach(alert -> {
            double currentPrice = productService.getCurrentPrice(alert.getProduct().getUrl());
            alert.setLastChecked(LocalDateTime.now());

            if (currentPrice <= alert.getTargetPrice()) {
                alert.setTriggered(true);
                alert.setActive(false);
                notificationService.sendNotification(alert, currentPrice);
            }

            alertRepository.save(alert);
        });
    }

    private AlertResponse convertToResponse(PriceAlert alert) {

        return AlertResponse.builder()
                .id(alert.getId())
                .productUrl(alert.getProduct().getUrl())
                .productName(alert.getProduct().getName())
                .currentPrice(alert.getProduct().getCurrentPrice())
                .targetPrice(alert.getTargetPrice())
                .userEmail(alert.getUserEmail())
                .checkFrequency(alert.getCheckFrequency())
                .active(alert.isActive())
                .triggered(alert.isTriggered())
                .createdAt(alert.getCreatedAt())
                .lastChecked(alert.getLastChecked())
                .build();
    }
}
