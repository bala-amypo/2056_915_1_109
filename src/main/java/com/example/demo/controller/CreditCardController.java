package com.example.demo.controller;

import com.example.demo.entity.CreditCardRecord;
import com.example.demo.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CreditCardController {

    @Autowired
    private CreditCardService creditCardService;

    @PostMapping
    public CreditCardRecord addCard(@RequestBody CreditCardRecord card) {
        return creditCardService.addCard(card);
    }

    @PutMapping("/{id}")
    public CreditCardRecord updateCard(@PathVariable Long id,
                                       @RequestBody CreditCardRecord card) {
        return creditCardService.updateCard(id, card);
    }

    @GetMapping("/{id}")
    public CreditCardRecord getCardById(@PathVariable Long id) {
        return creditCardService.getCardById(id);
    }

    @GetMapping("/user/{userId}")
    public List<CreditCardRecord> getCardsByUser(@PathVariable Long userId) {
        return creditCardService.getCardsByUser(userId);
    }

    @GetMapping
    public List<CreditCardRecord> getAllCards() {
        return creditCardService.getAllCards();
    }
}
