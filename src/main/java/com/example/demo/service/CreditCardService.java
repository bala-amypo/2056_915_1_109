package com.example.demo.service;

import com.example.demo.entity.CreditCardRecord;
import java.util.List;

public interface CreditCardService {

    CreditCardRecord addCard(Long userId, CreditCardRecord card);

    List<CreditCardRecord> getCardsByUser(Long userId);
}