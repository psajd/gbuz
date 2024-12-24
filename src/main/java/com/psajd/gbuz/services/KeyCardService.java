package com.psajd.gbuz.services;

import com.psajd.gbuz.entities.KeyCard;
import com.psajd.gbuz.repositories.KeyCardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KeyCardService {
    private final KeyCardRepository keyCardRepository;

    public KeyCardService(KeyCardRepository keyCardRepository) {
        this.keyCardRepository = keyCardRepository;
    }

    public List<KeyCard> getAllKeyCards() {
        return keyCardRepository.findAll();
    }

    public Optional<KeyCard> getKeyCardById(Long id) {
        return keyCardRepository.findById(id);
    }

    public KeyCard saveKeyCard(KeyCard keyCard) {
        return keyCardRepository.save(keyCard);
    }

    public void deleteKeyCard(Long id) {
        keyCardRepository.deleteById(id);
    }

    public List<KeyCard> searchBySerialNumber(String serialNumber) {
        return keyCardRepository.findBySerialNumberContainingIgnoreCase(serialNumber);
    }
}