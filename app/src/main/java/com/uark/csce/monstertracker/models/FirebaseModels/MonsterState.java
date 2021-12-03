package com.uark.csce.monstertracker.models.FirebaseModels;

import com.uark.csce.monstertracker.models.Monster;

import java.util.ArrayList;
import java.util.List;

public class MonsterState {

    private CardState cardState;
    private List<Monster> monsters;

    public MonsterState() {
        monsters = new ArrayList<>();
        cardState = new CardState();
    }

    public CardState getCardState() {
        return cardState;
    }

    public void setCardState(CardState cardState) {
        this.cardState = cardState;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(List<Monster> monsters) {
        this.monsters = monsters;
    }
}
