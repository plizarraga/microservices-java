package com.sapientdemo.contacts_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sapientdemo.contacts_service.model.entities.PhoneNumber;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {
}