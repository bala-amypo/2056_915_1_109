
package com.example.demo.service.impl;

import com.example.demo.service.PurchaseIntentService;
import com.example.demo.repository.PurchaseIntentRecordRepository;
import com.example.demo.entity.PurchaseIntentRecord;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PurchaseIntentServiceImpl implements PurchaseIntentService {
    private final PurchaseIntentRecordRepository purchaseIntentRepository;

    public PurchaseIntentServiceImpl(PurchaseIntentRecordRepository purchaseIntentRepository) {
        this.purchaseIntentRepository = purchaseIntentRepository;
    }

    @Override
    public PurchaseIntentRecord createIntent(PurchaseIntentRecord intent) {
        return purchaseIntentRepository.save(intent);
    }

    @Override
    public List<PurchaseIntentRecord> getIntentsByUser(Long userId) {
        return purchaseIntentRepository.findByUserId(userId);
    }

    @Override
    public PurchaseIntentRecord getIntentById(Long id) {
        return purchaseIntentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Intent not found"));
    }

    @Override
    public List<PurchaseIntentRecord> getAllIntents() {
        return purchaseIntentRepository.findAll();
    }
}


------------------------------------------------------------------
package com.example.demo.service;

import com.example.demo.entity.PurchaseIntentRecord;
import java.util.List;

public interface PurchaseIntentService {
    PurchaseIntentRecord createIntent(PurchaseIntentRecord intent);
    List<PurchaseIntentRecord> getIntentsByUser(Long userId);
    PurchaseIntentRecord getIntentById(Long id);
    List<PurchaseIntentRecord> getAllIntents();
}
