@Override
public CreditCardRecord addCard(Long userId, CreditCardRecord card) {
    card.setUserId(userId);
    return creditCardRepository.save(card);
}