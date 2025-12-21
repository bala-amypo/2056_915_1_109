package com.example.demo.service.impl;

import com.example.demo.entity.CreditCardRecord;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CreditCardRecordRepository;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.service.CreditCardService;

import java.util.List;

public class CreditCardServiceImpl implements CreditCardService {

    private final CreditCardRecordRepository cardRepository;
    private final UserProfileRepository userProfileRepository;

    public CreditCardServiceImpl(CreditCardRecordRepository cardRepository, UserProfileRepository userProfileRepository) {
        this.cardRepository = cardRepository;
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public CreditCardRecord addCard(CreditCardRecord card) {
        if (!userProfileRepository.existsById(card.getUserId()))
            throw new BadRequestException("User does not exist");
        if (card.getAnnualFee() < 0)
            throw new BadRequestException("Annual fee must be >= 0");

        return cardRepository.save(card);
    }

    @Override
    public CreditCardRecord updateCard(Long id, CreditCardRecord updated) {
        CreditCardRecord card = getCardById(id);
        card.setCardName(updated.getCardName());
        card.setIssuer(updated.getIssuer());
        card.setCardType(updated.getCardType());
        card.setAnnualFee(updated.getAnnualFee());
        card.setStatus(updated.getStatus());
        return cardRepository.save(card);
    }

    @Override
    public List<CreditCardRecord> getCardsByUser(Long userId) {
        return cardRepository.findByUserId(userId);
    }

    @Override
    public CreditCardRecord getCardById(Long id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found"));
    }

    @Override
    public List<CreditCardRecord> getAllCards() {
        return cardRepository.findAll();
    }
}
