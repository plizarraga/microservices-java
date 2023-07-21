package com.sapientdemo.contacts_service.model.dtos;

import java.util.List;

import lombok.Builder;

@Builder
public record ContactResponse(Long id, String name, String email, String address, List<PhoneNumberResponse> phoneNumbers) {
}