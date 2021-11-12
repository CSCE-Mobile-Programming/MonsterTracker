package com.uark.csce.monstertracker.models.info;

public class MonsterStatsInfo {
    private int Level;
    private StatsInfo Normal;
    private StatsInfo Elite;

    public void setup() {
        Normal.setup();
        Elite.setup();
    }

    public int getLevel() {
        return Level;
    }
    public void setLevel(int level) {
        Level = level;
    }

    public StatsInfo getNormal() {
        return Normal;
    }
    public void setNormal(StatsInfo normal) {
        Normal = normal;
    }

    public StatsInfo getElite() {
        return Elite;
    }
    public void setElite(StatsInfo elite) {
        Elite = elite;
    }
}


