package com.uark.csce.monstertracker.models.FirebaseModels;

import com.uark.csce.monstertracker.models.Monster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonsterState {

    private CardState cardState;
    private Map<String, Monster> monsters;

    public MonsterState() {
        monsters = new HashMap<>();
        cardState = new CardState();
    }

    public CardState getCardState() {
        return cardState;
    }

    public void setCardState(CardState cardState) {
        this.cardState = cardState;
    }

    public Map<String, Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(Map<String, Monster> monsters) {
        this.monsters = monsters;
    }
}
