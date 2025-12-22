package com.example.demo.service.impl;

import com.example.demo.entity.PurchaseIntentRecord;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.PurchaseIntentRecordRepository;
import com.example.demo.service.PurchaseIntentService;

import java.util.List;

public class PurchaseIntentServiceImpl implements PurchaseIntentService {

    private final PurchaseIntentRecordRepository intentRepository;

    // ⚠ Constructor order EXACT
    public PurchaseIntentServiceImpl(PurchaseIntentRecordRepository intentRepository) {
        this.intentRepository = intentRepository;
    }

    @Override
    public PurchaseIntentRecord createIntent(PurchaseIntentRecord intent) {

        if (intent.getAmount() == null || intent.getAmount() <= 0) {
            throw new BadRequestException("Amount must be greater than 0");
        }

        return intentRepository.save(intent);
    }

    @Override
    public PurchaseIntentRecord getIntentById(Long id) {
        return intentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase intent not found"));
    }

    @Override
    public List<PurchaseIntentRecord> getIntentsByUser(Long userId) {
        return intentRepository.findByUserId(userId);
    }

    @Override
    public List<PurchaseIntentRecord> getAllIntents() {
        return intentRepository.findAll();
    }
}
