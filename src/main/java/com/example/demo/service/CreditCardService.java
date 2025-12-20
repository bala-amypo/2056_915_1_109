package com.example.demo.service;

import com.example.demo.entity.CreditCardRecord;

public interface CreditCardService {
    CreditCardRecord addCard(Long userId, CreditCardRecord card);
}