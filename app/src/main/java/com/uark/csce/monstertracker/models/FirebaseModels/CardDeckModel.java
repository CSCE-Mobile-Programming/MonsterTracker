package com.uark.csce.monstertracker.models.FirebaseModels;

import com.uark.csce.monstertracker.models.info.CardInfo;

import java.util.List;

public class CardDeckModel {

    private List<CardInfo> cards;
    private int nextCard;

    public List<CardInfo> getCards() {
        return cards;
    }

    public void setCards(List<CardInfo> cards) {
        this.cards = cards;
    }

    public int getNextCard() {
        return nextCard;
    }

    public void setNextCard(int nextCard) {
        this.nextCard = nextCard;
    }
}
