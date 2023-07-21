package com.sapientdemo.contacts_service.model.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactRequest {
    private String name;
    private String email;
    private String address;
    private List<PhoneNumberRequest> phoneNumbers;
}
