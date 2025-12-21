package com.example.demo.service.impl;

import com.example.demo.service.RecommendationEngineService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationEngineServiceImpl
        implements RecommendationEngineService {

    public RecommendationEngineServiceImpl() {
    }

    @Override
    public List<String> recommendCards(Long userId) {
        return List.of(
            "HDFC Cashback Card",
            "ICICI Amazon Pay",
            "SBI SimplyClick"
        );
    }
}
