
package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.PurchaseIntentRecord;
import java.util.List;

public interface PurchaseIntentRecordRepository extends JpaRepository<PurchaseIntentRecord, Long> {
    List<PurchaseIntentRecord> findByUserId(Long userId);
}
