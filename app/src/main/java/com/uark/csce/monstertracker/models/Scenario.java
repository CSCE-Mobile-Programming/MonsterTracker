package com.uark.csce.monstertracker.models;

import java.util.List;

public class Scenario {

    private String name;
    private List<Monster> monsters;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(List<Monster> monsters) {
        this.monsters = monsters;
    }
}
