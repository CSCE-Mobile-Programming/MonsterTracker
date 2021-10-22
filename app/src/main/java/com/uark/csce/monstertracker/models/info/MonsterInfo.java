package com.uark.csce.monstertracker.models.info;

import java.util.List;

public class MonsterInfo {
    private String name;
    private List<MonsterStatsInfo> stats;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<MonsterStatsInfo> getStats() {
        return stats;
    }
    public void setStats(List<MonsterStatsInfo> stats) {
        this.stats = stats;
    }
}
