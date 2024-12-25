package com.psajd.gbuz.repositories;

import com.psajd.gbuz.entities.KeyCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeyCardRepository extends JpaRepository<KeyCard, Long> {
    List<KeyCard> findBySerialNumberContainingIgnoreCase(String serialNumber);
}