package com.example.demo.controller;

import com.example.demo.entity.CreditCardRecord;
import com.example.demo.service.CreditCardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CreditCardController {
    
    private final CreditCardService cardService;
    
    public CreditCardController(CreditCardService cardService) {
        this.cardService = cardService;
    }
    
    @GetMapping("/user/{userId}")
    public List<CreditCardRecord> getCardsByUser(@PathVariable Long userId) {
        return cardService.getCardsByUser(userId);
    }
}