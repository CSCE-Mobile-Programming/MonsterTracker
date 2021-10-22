package com.uark.csce.monstertracker.models.info;

import java.util.List;

public class DeckInfo {
    private String Name;
    private List<CardInfo> Cards;

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        this.Name = name;
    }

    public List<CardInfo> getCards() {
        return Cards;
    }
    public void setCards(List<CardInfo> cards) {
        this.Cards = cards;
    }
}
