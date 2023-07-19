package com.sapientdemo.inventoryservice.utils;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.sapientdemo.inventoryservice.model.entities.Inventory;
import com.sapientdemo.inventoryservice.repositories.InventoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final InventoryRepository inventoryRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("Loading inventory data...");
        if (inventoryRepository.findAll().size() == 0) {
            inventoryRepository.saveAll(
                    List.of(
                            Inventory.builder().sku("LAPTOP-001").quantity(10L).build(),
                            Inventory.builder().sku("LAPTOP-002").quantity(20L).build(),
                            Inventory.builder().sku("PHONE-001").quantity(30L).build(),
                            Inventory.builder().sku("PHONE-002").quantity(15L).build(),
                            Inventory.builder().sku("COMPUTER-001").quantity(0L).build(),
                            Inventory.builder().sku("COMPUTER-002").quantity(10L).build(),
                            Inventory.builder().sku("MONITOR-001").quantity(20L).build(),
                            Inventory.builder().sku("MONITOR-002").quantity(30L).build(),
                            Inventory.builder().sku("CAMERA-001").quantity(15L).build(),
                            Inventory.builder().sku("CAMERA-002").quantity(0L).build()));

        }
        log.info("Inventory data loaded.");
    }
}