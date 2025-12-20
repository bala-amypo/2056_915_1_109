package com.example.demo.service;

import com.example.demo.entity.PurchaseIntentRecord;

public interface PurchaseIntentService {

    PurchaseIntentRecord createIntent(PurchaseIntentRecord intent);
}