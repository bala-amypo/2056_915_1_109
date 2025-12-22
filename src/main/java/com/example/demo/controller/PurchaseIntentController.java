package com.example.demo.controller;

import com.example.demo.entity.PurchaseIntentRecord;
import com.example.demo.service.PurchaseIntentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-intents")
public class PurchaseIntentController {

    @Autowired
    private PurchaseIntentService purchaseIntentService;

    @PostMapping
    public PurchaseIntentRecord createIntent(@RequestBody PurchaseIntentRecord intent) {
        return purchaseIntentService.createIntent(intent);
    }

    @GetMapping("/{id}")
    public PurchaseIntentRecord getIntentById(@PathVariable Long id) {
        return purchaseIntentService.getIntentById(id);
    }

    @GetMapping
    public List<PurchaseIntentRecord> getAllIntents() {
        return purchaseIntentService.getAllIntents();
    }
}
