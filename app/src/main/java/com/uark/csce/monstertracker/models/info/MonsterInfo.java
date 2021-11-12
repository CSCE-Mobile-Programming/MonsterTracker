package com.uark.csce.monstertracker.models.info;

import java.util.List;

public class MonsterInfo {
    private String Name;
    private int MaxCount;
    private DeckInfo Deck;
    private List<MonsterStatsInfo> Stats;

    public void setup() {
        if (MaxCount <= 0) {
            throw new RuntimeException("MaxCount should be greater than 0");
        }
        for(MonsterStatsInfo info : Stats) {
            info.setup();
        }
    }

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        this.Name = name;
    }

    public int getMaxCount() {
        return MaxCount;
    }
    public void setMaxCount(int maxCount) {
        MaxCount = maxCount;
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
