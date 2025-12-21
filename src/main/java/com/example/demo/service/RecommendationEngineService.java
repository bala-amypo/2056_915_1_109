package com.example.demo.service;

import java.util.List;

public interface RecommendationEngineService {
    List<String> recommendCards(Long userId);
}
