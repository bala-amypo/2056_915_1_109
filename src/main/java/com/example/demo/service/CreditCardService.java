package com.example.demo.service;

import com.example.demo.entity.CreditCardRecord;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreditCardService {

    public CreditCardRecord addCard(Long userId, CreditCardRecord card) {
       
        card.setUserId(userId);
        return card;
    }

    public List<CreditCardRecord> getCardsByUser(Long userId) {
        return new ArrayList<>();
    }
}
