package com.sapientdemo.products_service.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.sapientdemo.products_service.model.entities.Product;
import com.sapientdemo.products_service.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("Loading products data...");
        // Check if the product repository is empty
        if (productRepository.findAll().isEmpty()) {
            List<Product> sampleProducts = new ArrayList<>();

            // Sample laptops
            sampleProducts.add(createProduct("LAPTOP-001", "Laptop Model XYZ",
                    "Powerful laptop with the latest features.", 999.99, true));
            sampleProducts.add(createProduct("LAPTOP-002", "Slim Laptop ABC",
                    "Ultra-slim and lightweight laptop for portability.", 799.99, true));

            // Sample phones
            sampleProducts.add(createProduct("PHONE-001", "Smartphone PQR",
                    "High-performance smartphone with advanced camera.", 699.99, true));
            sampleProducts.add(createProduct("PHONE-002", "Budget Phone MNO",
                    "Affordable smartphone with decent features.", 249.99, true));

            // Sample computers
            sampleProducts.add(createProduct("COMPUTER-001", "Desktop Computer ABC",
                    "Powerful desktop computer for all your computing needs.", 1299.99, true));
            sampleProducts.add(createProduct("COMPUTER-002", "Gaming PC XYZ",
                    "High-end gaming PC for the ultimate gaming experience.", 1999.99, true));

            // Sample monitors
            sampleProducts.add(createProduct("MONITOR-001", "27-inch Monitor ABC",
                    "High-resolution monitor for crisp visuals.", 299.99, true));
            sampleProducts.add(createProduct("MONITOR-002", "Curved Monitor XYZ",
                    "Immersive curved monitor for an enhanced viewing experience.", 449.99, true));

            // Sample cameras
            sampleProducts.add(createProduct("CAMERA-001", "Digital Camera PQR",
                    "Feature-rich digital camera for capturing moments.", 499.99, true));
            sampleProducts.add(createProduct("CAMERA-002", "Mirrorless Camera MNO",
                    "Compact and versatile mirrorless camera.", 799.99, true));

            productRepository.saveAll(sampleProducts);
        }
        log.info("Products data loaded.");
    }

    private Product createProduct(String sku, String name, String description, double price, boolean status) {
        return Product.builder()
                .sku(sku)
                .name(name)
                .description(description)
                .price(price)
                .status(status)
                .build();
    }
}