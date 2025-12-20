package com.example.demo.service.impl;

import com.example.demo.entity.CreditCardRecord;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CreditCardRecordRepository;
import com.example.demo.service.CreditCardService;

import java.util.List;

public class CreditCardServiceImpl implements CreditCardService {

    private final CreditCardRecordRepository repo;

    public CreditCardServiceImpl(CreditCardRecordRepository repo) {
        this.repo = repo;
    }

    public CreditCardRecord addCard(CreditCardRecord c) {
        return repo.save(c);
    }

    public CreditCardRecord updateCard(Long id, CreditCardRecord c) {
        if (!repo.existsById(id))
            throw new ResourceNotFoundException("Card not found");
        c.setId(id);
        return repo.save(c);
    }

    public List<CreditCardRecord> getCardsByUser(Long userId) {
        return repo.findByUserId(userId);
    }

    public CreditCardRecord getCardById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found"));
    }

    public List<CreditCardRecord> getAllCards() {
        return repo.findAll();
    }
}