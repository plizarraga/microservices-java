package com.sapientdemo.contacts_service.services;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sapientdemo.contacts_service.model.dtos.ContactRequest;
import com.sapientdemo.contacts_service.model.dtos.ContactResponse;
import com.sapientdemo.contacts_service.model.dtos.PhoneNumberRequest;
import com.sapientdemo.contacts_service.model.dtos.PhoneNumberResponse;
import com.sapientdemo.contacts_service.model.entities.Contact;
import com.sapientdemo.contacts_service.model.entities.PhoneNumber;
import com.sapientdemo.contacts_service.repositories.ContactRepository;
import com.sapientdemo.contacts_service.repositories.PhoneNumberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactService {
    private final ContactRepository contactRepository;
    private final PhoneNumberRepository phoneNumberRepository;

    // Create a new contact
    public Contact addContact(ContactRequest contactRequest) {
        // Convert ContactRequest to Contact entity and save the contact
        Contact contact = convertToContactEntity(contactRequest);
        Contact savedContact = contactRepository.save(contact);

        // Convert PhoneNumberRequest list to PhoneNumber entities and associate them
        // with the contact
        List<PhoneNumber> phoneNumbers = convertToPhoneNumberEntities(contactRequest.getPhoneNumbers(), savedContact);
        phoneNumberRepository.saveAll(phoneNumbers);

        log.info("Contact added: {}", savedContact);

        return savedContact;
    }

    // Get all contacts
    public List<ContactResponse> getAllContacts() {
        var contacts = contactRepository.findAll();

        return contacts.stream().map(this::mapToContactResponse).toList();
    }

    // Get a contact by ID
    public ContactResponse getContactById(Long id) {
        var contact = contactRepository.findById(id).orElse(null);
        return contact != null ? mapToContactResponse(contact) : null;
    }

    // Delete a contact by ID
    public boolean deleteContact(Long id) {
        var contact = contactRepository.findById(id).orElse(null);

        if (contact != null) {
            contactRepository.delete(contact);
            log.info("Contact deleted: {}", contact);
            return true;
        }

        return false;
    }

    public boolean updateContact(Long id, ContactRequest contactRequest) {
        var existingContact = contactRepository.findById(id).orElse(null);

        if (contactRequest != null) {

            // Update Name if present in the request
            if (contactRequest.getName() != null) {
                existingContact.setName(contactRequest.getName());
            }

            // Update Email if present in the request
            if (contactRequest.getEmail() != null) {
                existingContact.setEmail(contactRequest.getEmail());
            }

            // Update Address if present in the request
            if (contactRequest.getAddress() != null) {
                existingContact.setAddress(contactRequest.getAddress());
            }

            // Delete old phone numbers not present in the ContactRequest
            List<PhoneNumber> oldPhoneNumbers = existingContact.getPhoneNumbers();
            List<PhoneNumber> phoneNumbersToDelete = oldPhoneNumbers.stream()
                    .filter(phoneNumber -> !containsPhoneNumber(phoneNumber, contactRequest.getPhoneNumbers()))
                    .collect(Collectors.toList());

            phoneNumberRepository.deleteAll(phoneNumbersToDelete);

            // Insert new phone numbers from the ContactRequest
            List<PhoneNumber> updatedPhoneNumbers = convertToPhoneNumberEntities(contactRequest.getPhoneNumbers(),
                    existingContact);
            existingContact.setPhoneNumbers(updatedPhoneNumbers);

            // Save the updated contact
            contactRepository.save(existingContact);

            log.info("Contact updated: {}", existingContact);
            return true; // Contact updated successfully
        }

        return false; // Contact with the provided id not found
    }

    private boolean containsPhoneNumber(PhoneNumber phoneNumber, List<PhoneNumberRequest> phoneNumberRequests) {
        return phoneNumberRequests.stream().anyMatch(request -> phoneNumber.getNumber().equals(request.getNumber()));
    }

    private Contact convertToContactEntity(ContactRequest contactRequest) {
        return Contact.builder()
                .name(contactRequest.getName())
                .email(contactRequest.getEmail())
                .address(contactRequest.getAddress())
                .phoneNumbers(Collections.emptyList()) // Initialize with an empty list
                .build();
    }

    private List<PhoneNumber> convertToPhoneNumberEntities(List<PhoneNumberRequest> phoneNumberRequests,
            Contact contact) {
        return phoneNumberRequests.stream()
                .map(phoneNumberRequest -> PhoneNumber.builder()
                        .number(phoneNumberRequest.getNumber())
                        .description(phoneNumberRequest.getDescription())
                        .contact(contact) // Associate phone number with the contact
                        .build())
                .collect(Collectors.toList());
    }

    private ContactResponse mapToContactResponse(Contact contact) {
        List<PhoneNumberResponse> phoneNumbers = contact.getPhoneNumbers()
                .stream()
                .map(this::mapToPhoneNumberResponse)
                .collect(Collectors.toList());

        return ContactResponse.builder()
                .id(contact.getId())
                .name(contact.getName())
                .email(contact.getEmail())
                .address(contact.getAddress())
                .phoneNumbers(phoneNumbers)
                .build();
    }

    private PhoneNumberResponse mapToPhoneNumberResponse(PhoneNumber phoneNumber) {
        return PhoneNumberResponse.builder()
                .id(phoneNumber.getId())
                .number(phoneNumber.getNumber())
                .description(phoneNumber.getDescription())
                .build();
    }
}
