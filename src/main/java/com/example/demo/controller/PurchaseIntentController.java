package com.example.demo.controller;

import com.example.demo.entity.PurchaseIntentRecord;
import com.example.demo.service.PurchaseIntentService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/intents")
public class PurchaseIntentController {
    private final PurchaseIntentService service;

    public PurchaseIntentController(PurchaseIntentService service) {
        this.service = service;
    }

    @PostMapping
    public PurchaseIntentRecord create(@RequestBody PurchaseIntentRecord intent) {
        return service.createIntent(intent);
    }

    @GetMapping("/user/{userId}")
    public List<PurchaseIntentRecord> getByUser(@PathVariable Long userId) {
        return service.getIntentsByUser(userId);
    }

    @GetMapping
    public List<PurchaseIntentRecord> list() {
        return service.getAllIntents();
    }
}//PurchaseIntentController