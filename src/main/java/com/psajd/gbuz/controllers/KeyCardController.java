package com.psajd.gbuz.controllers;

import com.psajd.gbuz.entities.KeyCard;
import com.psajd.gbuz.services.KeyCardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/keycards")
public class KeyCardController {

    private final KeyCardService keyCardService;

    public KeyCardController(KeyCardService keyCardService) {
        this.keyCardService = keyCardService;
    }

    @GetMapping
    public List<KeyCard> getAllKeyCards() {
        return keyCardService.getAllKeyCards();
    }

    @GetMapping("/{id}")
    public ResponseEntity<KeyCard> getKeyCardById(@PathVariable Long id) {
        Optional<KeyCard> keyCard = keyCardService.getKeyCardById(id);
        return keyCard.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public KeyCard createKeyCard(@RequestBody KeyCard keyCard) {
        return keyCardService.saveKeyCard(keyCard);
    }

    @PutMapping("/{id}")
    public ResponseEntity<KeyCard> updateKeyCard(@PathVariable Long id, @RequestBody KeyCard updatedKeyCard) {
        return keyCardService.getKeyCardById(id)
                .map(keyCard -> {
                    updatedKeyCard.setId(id);
                    return ResponseEntity.ok(keyCardService.saveKeyCard(updatedKeyCard));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKeyCard(@PathVariable Long id) {
        if (keyCardService.getKeyCardById(id).isPresent()) {
            keyCardService.deleteKeyCard(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public List<KeyCard> searchKeyCards(@RequestParam String serialNumber) {
        return keyCardService.searchBySerialNumber(serialNumber);
    }
}