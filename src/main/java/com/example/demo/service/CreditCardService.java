package com.example.demo.service;

import com.example.demo.entity.CreditCardRecord;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface CreditCardService {

    CreditCardRecord addCard(CreditCardRecord card);

    CreditCardRecord updateCard(Long id, CreditCardRecord updated);

    CreditCardRecord getCardById(Long id);

    List<CreditCardRecord> getCardsByUser(Long userId);

    List<CreditCardRecord> getAllCards();
}
