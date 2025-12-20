package com.example.demo.service.impl;

import com.example.demo.entity.PurchaseIntentRecord;
import com.example.demo.repository.PurchaseIntentRecordRepository;
import com.example.demo.service.PurchaseIntentService;

import java.util.List;

public class PurchaseIntentServiceImpl implements PurchaseIntentService {

    private final PurchaseIntentRecordRepository repo;

    public PurchaseIntentServiceImpl(PurchaseIntentRecordRepository repo) {
        this.repo = repo;
    }

    public PurchaseIntentRecord createIntent(PurchaseIntentRecord i) {
        return repo.save(i);
    }

    public List<PurchaseIntentRecord> getIntentsByUser(Long userId) {
        return repo.findByUserId(userId);
    }

    public PurchaseIntentRecord getIntentById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public List<PurchaseIntentRecord> getAllIntents() {
        return repo.findAll();
    }
}