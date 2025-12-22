package com.example.demo.repository;

import com.example.demo.entity.PurchaseIntentRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseIntentRepository
        extends JpaRepository<PurchaseIntentRecord, Long> {
}
