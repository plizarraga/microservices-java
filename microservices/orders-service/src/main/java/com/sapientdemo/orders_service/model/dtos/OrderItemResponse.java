package com.sapientdemo.orders_service.model.dtos;

public record OrderItemResponse(Long id, String sku, Double price, Long quantity) {
}