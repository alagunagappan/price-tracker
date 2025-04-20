package com.pricetracker.service;

import com.pricetracker.model.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private static final String MOCK_PRODUCTS_FILE = "data/products.json";

    public Optional<Product> getProductByUrl(String url) {
        // In a real implementation, this would scrape the website or use an API
        // For demo purposes, we'll use a static JSON file

        try {
            ClassPathResource resource = new ClassPathResource(MOCK_PRODUCTS_FILE);
            ObjectMapper mapper = new ObjectMapper();
            List<Product> products = mapper.readValue(resource.getInputStream(), new TypeReference<>() {});

            return products.stream()
                    .filter(p -> p.getUrl().equals(url))
                    .findFirst();

        } catch (IOException e) {
            throw new RuntimeException("Failed to load mock products", e);
        }
    }

    public double getCurrentPrice(String productUrl) {
        return getProductByUrl(productUrl)
                .map(Product::getCurrentPrice)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }
}
