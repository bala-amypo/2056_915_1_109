package com.example.demo.service;

import com.example.demo.entity.PurchaseIntentRecord;
import org.springframework.stereotype.Service;

@Service
public class PurchaseIntentService {

    public PurchaseIntentRecord save(PurchaseIntentRecord intent) {
        return intent;
    }
}
