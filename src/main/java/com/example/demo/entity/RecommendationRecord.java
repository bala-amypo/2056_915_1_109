package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "recommendation")
public class RecommendationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String recommendationText;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRecommendationText() { return recommendationText; }
    public void setRecommendationText(String recommendationText) {
        this.recommendationText = recommendationText;
    }
}
