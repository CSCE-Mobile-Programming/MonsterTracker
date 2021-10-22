package com.uark.csce.monstertracker.models.info;

public class StatsInfo {
    private int Health;
    private int Move;
    private int Attack;
    private int Range;

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
}
