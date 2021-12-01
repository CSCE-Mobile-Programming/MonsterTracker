package com.uark.csce.monstertracker.models.FirebaseModels;

public class MainActivityInfo {

    private String monsterInfoName;
    private int initiative;
    private int numMonsters;

    public String getMonsterInfoName() {
        return monsterInfoName;
    }

    public void setMonsterInfoName(String monsterInfoName) {
        this.monsterInfoName = monsterInfoName;
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
