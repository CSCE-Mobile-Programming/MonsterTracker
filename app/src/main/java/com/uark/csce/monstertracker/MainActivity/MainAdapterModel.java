package com.uark.csce.monstertracker.MainActivity;

import com.uark.csce.monstertracker.models.info.MonsterInfo;

public class MainAdapterModel {
    private MonsterInfo info;
    private int  initiative;
    private int numMonsters;

    public MonsterInfo getInfo() {
        return info;
    }

    public void setInfo(MonsterInfo info) {
        this.info = info;
    }

    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public int getNumMonsters() {
        return numMonsters;
    }

    public void setNumMonsters(int numMonsters) {
        this.numMonsters = numMonsters;
    }
}
