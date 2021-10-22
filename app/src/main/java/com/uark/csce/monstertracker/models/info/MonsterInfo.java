package com.uark.csce.monstertracker.models.info;

import java.util.List;

public class MonsterInfo {
    private String Name;
    private DeckInfo Deck;
    private List<MonsterStatsInfo> Stats;

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        this.Name = name;
    }

    public List<MonsterStatsInfo> getStats() {
        return Stats;
    }
    public void setStats(List<MonsterStatsInfo> stats) {
        this.Stats = stats;
    }

    public DeckInfo getDeck() {
        return Deck;
    }
    public void setDeck(DeckInfo deck) {
        this.Deck = deck;
    }
}
