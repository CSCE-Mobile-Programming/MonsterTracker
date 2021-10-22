package com.uark.csce.monstertracker.models.info;

import java.util.List;

public class DeckInfo {
    private String name;
    private List<CardInfo> cards;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<CardInfo> getCards() {
        return cards;
    }
    public void setCards(List<CardInfo> cards) {
        this.cards = cards;
    }
}
