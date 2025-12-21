package com.example.demo.service.impl;

import com.example.demo.entity.PurchaseIntent;
import com.example.demo.repository.PurchaseIntentRepository;
import com.example.demo.service.PurchaseIntentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service   // 🔥 THIS IS THE MOST IMPORTANT LINE
public class PurchaseIntentServiceImpl implements PurchaseIntentService {

    private final PurchaseIntentRepository purchaseIntentRepository;

    public PurchaseIntentServiceImpl(PurchaseIntentRepository purchaseIntentRepository) {
        this.purchaseIntentRepository = purchaseIntentRepository;
    }

    @Override
    public PurchaseIntent createPurchaseIntent(PurchaseIntent intent) {
        return purchaseIntentRepository.save(intent);
    }

    @Override
    public PurchaseIntent getPurchaseIntentById(Long id) {
        return purchaseIntentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase Intent not found"));
    }

    @Override
    public List<PurchaseIntent> getAllPurchaseIntents() {
        return purchaseIntentRepository.findAll();
    }
}
