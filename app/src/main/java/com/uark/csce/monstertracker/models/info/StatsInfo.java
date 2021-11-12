package com.uark.csce.monstertracker.models.info;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatsInfo {
    private int Health;
    private int Move;
    private int Attack;
    private int Range;
    private String[] Attributes;
    private AttributesInfo AttributesInfo;

    public void setup() {
        // Load attributes into attributes info
        AttributesInfo = new AttributesInfo();
        AttributesInfo.setAttributesList(new ArrayList<>(Arrays.asList(Attributes)));
    }

    public int getHealth() {
        return Health;
    }
    public void setHealth(int health) {
        Health = health;
    }

    public int getMove() {
        return Move;
    }
    public void setMove(int move) {
        Move = move;
    }

    public int getAttack() {
        return Attack;
    }
    public void setAttack(int attack) {
        Attack = attack;
    }

    public int getRange() {
        return Range;
    }
    public void setRange(int range) {
        Range = range;
    }

    public AttributesInfo getAttributesInfo() {
        return AttributesInfo;
    }
}
