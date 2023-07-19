package com.sapientdemo.products_service.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sapientdemo.products_service.model.dtos.ProductRequest;
import com.sapientdemo.products_service.model.dtos.ProductResponse;
import com.sapientdemo.products_service.model.entities.Product;
import com.sapientdemo.products_service.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public void addProduct(ProductRequest productRequest) {
        var product = Product.builder()
                .sku(productRequest.getSku())
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .status(productRequest.getStatus())
                .build();

        productRepository.save(product);

        log.info("Product added: {}", product);
    }

    public boolean updateProduct(Long id, ProductRequest productRequest) {
        var existingProduct = productRepository.findById(id).orElse(null);

        if (existingProduct != null) {

            // Update SKU if present in the request
            if (productRequest.getSku() != null) {
                existingProduct.setSku(productRequest.getSku());
            }

            // Update name if present in the request
            if (productRequest.getName() != null) {
                existingProduct.setName(productRequest.getName());
            }

            // Update description if present in the request
            if (productRequest.getDescription() != null) {
                existingProduct.setDescription(productRequest.getDescription());
            }

            // Update price if present in the request
            if (productRequest.getPrice() != null) {
                existingProduct.setPrice(productRequest.getPrice());
            }

            // Update status if present in the request
            if (productRequest.getStatus() != null) {
                existingProduct.setStatus(productRequest.getStatus());
            }

            productRepository.save(existingProduct);

            log.info("Product updated: {}", existingProduct);
            return true;
        }

        return false;
    }

    public List<ProductResponse> getAllProducts() {
        var products = productRepository.findAll();

        return products.stream().map(this::mapToProductResponse).toList();
    }

    public ProductResponse getProductById(Long id) {
        var product = productRepository.findById(id).orElse(null);
        return product != null ? mapToProductResponse(product) : null;
    }

    public boolean deleteProduct(Long id) {
        var product = productRepository.findById(id).orElse(null);

        if (product != null) {
            productRepository.delete(product);
            log.info("Product deleted: {}", product);
            return true;
        }

        return false;
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .sku(product.getSku())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .status(product.getStatus())
                .build();
    }
}
