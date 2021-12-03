package com.uark.csce.monstertracker.models.FirebaseModels;

import com.uark.csce.monstertracker.models.info.CardInfo;

import java.util.ArrayList;
import java.util.List;

public class CardState {

    private List<CardInfo> cards;
    private CardInfo currentCard;
    private int nextCard;

    public CardState() {
        currentCard = new CardInfo();
        currentCard.setImagePath("");

        cards = new ArrayList<>();
    }

    public List<CardInfo> getCards() {
        return cards;
    }

    public void setCards(List<CardInfo> cards) {
        this.cards = cards;
    }

    public CardInfo getCurrentCard() {
        return currentCard;
    }

    public void setCurrentCard(CardInfo currentCard) {
        this.currentCard = currentCard;
    }

    public int getNextCard() {
        return nextCard;
    }

    public void setNextCard(int nextCard) {
        this.nextCard = nextCard;
    }
}
