package com.sapientdemo.contacts_service.model.dtos;

import lombok.Builder;

@Builder
public record PhoneNumberResponse(Long id, String number, String description) {
}