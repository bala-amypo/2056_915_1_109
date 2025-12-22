package com.example.demo.service;

import com.example.demo.entity.PurchaseIntent;
import java.util.List;

public interface PurchaseIntentService {

    PurchaseIntent createPurchaseIntent(PurchaseIntent intent);

    PurchaseIntent getPurchaseIntentById(Long id);

    List<PurchaseIntent> getAllPurchaseIntents();

    PurchaseIntent updatePurchaseIntent(Long id, PurchaseIntent intent);

    void deletePurchaseIntent(Long id);
}
