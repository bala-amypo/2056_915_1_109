package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "credit_card")
public class CreditCardRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardName;
    private String bank;
    private int rewardRate;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCardName() { return cardName; }
    public void setCardName(String cardName) { this.cardName = cardName; }

    public String getBank() { return bank; }
    public void setBank(String bank) { this.bank = bank; }

    public int getRewardRate() { return rewardRate; }
    public void setRewardRate(int rewardRate) { this.rewardRate = rewardRate; }
}
