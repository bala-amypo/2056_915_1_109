package com.example.demo.service.impl;

import org.springframework.stereotype.Service;

import com.example.demo.entity.CreditCardRecord;
import com.example.demo.repository.CreditCardRecordRepository;
import com.example.demo.service.CreditCardService;

@Service
public class CreditCardServiceImpl implements CreditCardService {

    private final CreditCardRecordRepository creditCardRepository;

    public CreditCardServiceImpl(CreditCardRecordRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    @Override
    public CreditCardRecord addCard(Long userId, CreditCardRecord card) {
        card.setUserId(userId);
        return creditCardRepository.save(card);
    }
}