package com.example.demo.controller;

import com.example.demo.entity.CreditCardRecord;
import com.example.demo.service.CreditCardService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CreditCardController {

    private final CreditCardService creditCardService;

    public CreditCardController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @PostMapping
    public ResponseEntity<CreditCardRecord> addCard(
            @RequestParam Long userId,
            @Valid @RequestBody CreditCardRecord card) {

        return ResponseEntity.ok(creditCardService.addCard(userId, card));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CreditCardRecord>> getCardsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(creditCardService.getCardsByUser(userId));
    }
}
