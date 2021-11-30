package com.uark.csce.monstertracker.models;

import com.uark.csce.monstertracker.models.info.MonsterInfo;
import com.uark.csce.monstertracker.models.info.MonsterStatsInfo;
import com.uark.csce.monstertracker.models.info.StatsInfo;

// A monster is a single instance of a Class of Monsters ("Class" in the game sense and not in the
// OOP sense). I.e. one monster object maps to one monster on the board in GH
public class Monster {
    private MonsterInfo info; // references the monsterInfo used to generate this instance
    private int level;
    private MonsterType type;

    // Stats. "Base" stats are given by level without modifiers from attack cards.
    private int baseHealth;
    private int baseMove;
    private int baseAttack;
    private int baseRange;

    // Non-base stats are fluid and change-turn-to-turn. A monster is defeated if its Health is 0
    private int health;
    private int move;
    private int attack;
    private int range;

    private Attributes attributes;

    public Monster(MonsterInfo info, int level, MonsterType type) {
        this.info = info;
        this.level = level;
        this.type = type;

        MonsterStatsInfo stats = info.getStats().get(level);
        if (type == MonsterType.Normal) {
            baseHealth = stats.getNormal().getHealth();
            baseMove = stats.getNormal().getMove();
            baseAttack = stats.getNormal().getAttack();
            baseRange = stats.getNormal().getRange();
        }
        else if (type == MonsterType.Elite) {
            baseHealth = stats.getElite().getHealth();
            baseMove = stats.getElite().getMove();
            baseAttack = stats.getElite().getAttack();
            baseRange = stats.getElite().getRange();
        }

        health = baseHealth;
        move = baseMove;
        attack = baseAttack;
        range = baseRange;

        attributes = new Attributes();
    }

    // Getters and setters
    public MonsterInfo getInfo() {
        return info;
    }
    public void setInfo(MonsterInfo info) {
        this.info = info;
    }

    public Attributes getAttributes() {
        return attributes;
    }
    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }

    public MonsterType getType() {
        return type;
    }
    public void setType(MonsterType type) {
        this.type = type;
    }

    public int getBaseHealth() {
        return baseHealth;
    }
    public void setBaseHealth(int baseHealth) {
        this.baseHealth = baseHealth;
    }

    public int getBaseMove() {
        return baseMove;
    }
    public void setBaseMove(int baseMove) {
        this.baseMove = baseMove;
    }

    public int getBaseAttack() {
        return baseAttack;
    }
    public void setBaseAttack(int baseAttack) {
        this.baseAttack = baseAttack;
    }

    public int getBaseRange() {
        return baseRange;
    }
    public void setBaseRange(int baseRange) {
        this.baseRange = baseRange;
    }

    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }

    public int getMove() {
        return move;
    }
    public void setMove(int move) {
        this.move = move;
    }

    public int getAttack() {
        return attack;
    }
    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getRange() {
        return range;
    }
    public void setRange(int range) {
        this.range = range;
    }
}
