package com.uark.csce.monstertracker.models.info;

import java.util.List;

public class Scenario {
    private String Name;
    private List<String> Monsters;

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        this.Name = name;
    }

    public List<String> getMonsters() {
        return Monsters;
    }
    public void setMonsters(List<String> monsters) {
        this.Monsters = monsters;
    }
}
