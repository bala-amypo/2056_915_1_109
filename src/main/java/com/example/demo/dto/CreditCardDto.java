package com.example.demo.dto;

public class CreditCardDto {
    private Long id;
    private Long userId;
    private String cardName;
    private String issuer;
    private String cardType;
    private Double annualFee;
    private String status;
    private java.time.LocalDateTime createdAt;

    // Default constructor (for JSON deserialization)
    public CreditCardDto() {}

    // Parameterized constructor (for creating from entity)
    public CreditCardDto(Long id, Long userId, String cardName, String issuer, 
                        String cardType, Double annualFee, String status, 
                        java.time.LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.cardName = cardName;
        this.issuer = issuer;
        this.cardType = cardType;
        this.annualFee = annualFee;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Constructor from CreditCardRecord entity
    public CreditCardDto(com.example.demo.entity.CreditCardRecord card) {
        this.id = card.getId();
        this.userId = card.getUserId();
        this.cardName = card.getCardName();
        this.issuer = card.getIssuer();
        this.cardType = card.getCardType();
        this.annualFee = card.getAnnualFee();
        this.status = card.getStatus();
        this.createdAt = card.getCreatedAt();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Double getAnnualFee() {
        return annualFee;
    }

    public void setAnnualFee(Double annualFee) {
        this.annualFee = annualFee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public java.time.LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.time.LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
