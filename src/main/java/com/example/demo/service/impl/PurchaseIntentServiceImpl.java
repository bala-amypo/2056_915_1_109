
package com.example.demo.service.impl;

import com.example.demo.entity.PurchaseIntentRecord;
import com.example.demo.repository.PurchaseIntentRecordRepository;
import com.example.demo.service.PurchaseIntentService;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class PurchaseIntentServiceImpl implements PurchaseIntentService {
    private final PurchaseIntentRecordRepository repo;

    public PurchaseIntentServiceImpl(PurchaseIntentRecordRepository repo) { this.repo = repo; }

    public PurchaseIntentRecord createIntent(PurchaseIntentRecord intent) { return repo.save(intent); }
    public List<PurchaseIntentRecord> getIntentsByUser(Long userId) { return repo.findByUserId(userId); }
    public PurchaseIntentRecord getIntentById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Intent not found"));
    }
    public List<PurchaseIntentRecord> getAllIntents() { return repo.findAll(); }
}
