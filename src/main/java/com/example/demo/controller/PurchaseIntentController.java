package com.example.demo.controller;

import com.example.demo.entity.PurchaseIntent;
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
    public PurchaseIntent createIntent(@RequestBody PurchaseIntent intent) {
        return purchaseIntentService.createIntent(intent);
    }

    @GetMapping("/{id}")
    public PurchaseIntent getIntentById(@PathVariable Long id) {
        return purchaseIntentService.getIntentById(id);
    }

    @GetMapping
    public List<PurchaseIntent> getAllIntents() {
        return purchaseIntentService.getAllIntents();
    }
}
