package com.example.demo.service;

import com.example.demo.entity.RecommendationRecord;
import org.springframework.stereotype.Service;

@Service
public class RecommendationEngineService {

    public RecommendationRecord recommend() {
        return new RecommendationRecord();
    }
}
