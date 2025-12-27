
package com.example.demo.service.impl;

import com.example.demo.entity.CreditCardRecord;
import com.example.demo.repository.CreditCardRecordRepository;
import com.example.demo.service.CreditCardService;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class CreditCardServiceImpl implements CreditCardService {
    private final CreditCardRecordRepository cardRepo;

    public CreditCardServiceImpl(CreditCardRecordRepository cardRepo) {
        this.cardRepo = cardRepo;
    }

    @Override
    public CreditCardRecord addCard(CreditCardRecord card) {
        // FIX: Check if annualFee is not null before comparing it to 0
        if (card.getAnnualFee() != null && card.getAnnualFee() < 0) {
            throw new BadRequestException("Fee must be non-negative");
        }
        return cardRepo.save(card);
    }

    @Override
    public CreditCardRecord updateCard(Long id, CreditCardRecord updated) {
        CreditCardRecord existing = getCardById(id);
        existing.setCardName(updated.getCardName());
        existing.setAnnualFee(updated.getAnnualFee());
        existing.setStatus(updated.getStatus());
        return cardRepo.save(existing);
    }

    @Override
    public List<CreditCardRecord> getCardsByUser(Long userId) {
        return cardRepo.findByUserId(userId);
    }

    @Override
    public CreditCardRecord getCardById(Long id) {
        return cardRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Card not found"));
    }

    @Override
    public List<CreditCardRecord> getAllCards() {
        return cardRepo.findAll();
    }
}
