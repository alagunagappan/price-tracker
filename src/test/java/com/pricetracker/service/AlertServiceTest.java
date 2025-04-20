package com.pricetracker.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.pricetracker.dto.CreateAlertRequest;
import com.pricetracker.model.Frequency;
import com.pricetracker.model.PriceAlert;
import com.pricetracker.model.Product;
import com.pricetracker.repository.PriceAlertRepository;
import com.pricetracker.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

class AlertServiceTest {

    @Mock
    private PriceAlertRepository alertRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductService productService;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private AlertService alertService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAlert_WithNewProduct_CreatesAlertSuccessfully() {
        // Arrange
        CreateAlertRequest request = new CreateAlertRequest();
        request.setProductUrl("http://com/product1");
        request.setTargetPrice(100.0);
        request.setUserEmail("user@com");
        request.setCheckFrequency(Frequency.DAILY);

        Product product = new Product();
        product.setName("Test Product");
        product.setUrl(request.getProductUrl());
        product.setCurrentPrice(120.0);

        PriceAlert priceAlert = PriceAlert.builder()
                .id(1L)
                .product(product)
                .targetPrice(request.getTargetPrice())
                .userEmail(request.getUserEmail())
                .checkFrequency(request.getCheckFrequency())
                .active(true)
                .triggered(false)
                .createdAt(LocalDateTime.now())
                .lastChecked(null)
                .build();

        when(productRepository.findByUrl(request.getProductUrl())).thenReturn(Optional.of(product));
        when(alertRepository.save(any(PriceAlert.class))).thenReturn(priceAlert);

        var result = alertService.createAlert(request);

        // Assert
        assertNotNull(result);
        assertEquals(request.getProductUrl(), result.getProductUrl());
        assertEquals(request.getTargetPrice(), result.getTargetPrice());
        assertEquals(120.0, result.getCurrentPrice());
    }

    @Test
    void checkAndTriggerAlerts_WhenPriceDrops_SendNotification() {
        // Arrange
        Product product = new Product();
        product.setUrl("http://com/product1");
        product.setCurrentPrice(90.0); // Current price is below target

        PriceAlert alert = PriceAlert.builder()
                .id(1L)
                .product(product)
                .targetPrice(100.0)
                .build();

        when(alertRepository.findActiveAlertsByFrequency(any(Frequency.class))).thenReturn(List.of(alert));
        when(productService.getCurrentPrice(product.getUrl())).thenReturn(90.0);

        // Act
        alertService.checkAndTriggerAlerts(Frequency.DAILY);

        // Assert
        verify(notificationService).sendNotification(alert, 90.0);
        assertFalse(alert.isActive());
        assertTrue(alert.isTriggered());
    }
}
