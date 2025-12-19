package com.example.demo.service;

import com.example.demo.entity.CreditCardRecord;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditCardService {

    public CreditCardRecord save(CreditCardRecord card) {
        return card;
    }

    public List<CreditCardRecord> findAll() {
        return List.of();
    }
}
