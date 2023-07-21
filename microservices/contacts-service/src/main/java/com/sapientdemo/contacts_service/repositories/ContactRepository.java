package com.sapientdemo.contacts_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sapientdemo.contacts_service.model.entities.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}