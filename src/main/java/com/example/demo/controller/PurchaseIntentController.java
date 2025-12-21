package com.example.demo.controller;

import com.example.demo.entity.PurchaseIntentRecord;
import com.example.demo.service.PurchaseIntentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchase-intents")
public class PurchaseIntentController {

    private final PurchaseIntentService intentService;

    public PurchaseIntentController(PurchaseIntentService intentService) {
        this.intentService = intentService;
    }

    @PostMapping
    public PurchaseIntentRecord createIntent(@RequestBody PurchaseIntentRecord intent) {
        return intentService.createIntent(intent);
    }

    @GetMapping("/{id}")
    public PurchaseIntentRecord getIntentById(@PathVariable Long id) {
        return intentService.getIntentById(id);
    }

    @GetMapping("/user/{userId}")
    public List<PurchaseIntentRecord> getIntentsByUser(@PathVariable Long userId) {
        return intentService.getIntentsByUser(userId);
    }

    @GetMapping
    public List<PurchaseIntentRecord> getAllIntents() {
        return intentService.getAllIntents();
    }
}
