package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "reward_rule")
public class RewardRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;
    private int pointsPerRupee;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getPointsPerRupee() { return pointsPerRupee; }
    public void setPointsPerRupee(int pointsPerRupee) {
        this.pointsPerRupee = pointsPerRupee;
    }
}
