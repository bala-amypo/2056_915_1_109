package com.example.demo.controller;

import com.example.demo.entity.PurchaseIntentRecord;
import com.example.demo.service.PurchaseIntentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/intents")
public class PurchaseIntentController {
    
    private final PurchaseIntentService intentService;
    
    public PurchaseIntentController(PurchaseIntentService intentService) {
        this.intentService = intentService;
    }
    
    @GetMapping("/user/{userId}")
    public List<PurchaseIntentRecord> getIntentsByUser(@PathVariable Long userId) {
        return intentService.getIntentsByUser(userId);
    }
}