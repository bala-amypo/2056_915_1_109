package com.example.demo.service.impl;

import com.example.demo.entity.PurchaseIntentRecord;
import com.example.demo.repository.PurchaseIntentRecordRepository;
import com.example.demo.service.PurchaseIntentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseIntentServiceImpl implements PurchaseIntentService {

    private final PurchaseIntentRecordRepository repository;

    public PurchaseIntentServiceImpl(PurchaseIntentRecordRepository repository) {
        this.repository = repository;
    }

    @Override
    public PurchaseIntentRecord createIntent(PurchaseIntentRecord intent) {
        return repository.save(intent);
    }

    @Override
    public PurchaseIntentRecord getIntentById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<PurchaseIntentRecord> getIntentsByUser(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public List<PurchaseIntentRecord> getAllIntents() {
        return repository.findAll();
    }
}
