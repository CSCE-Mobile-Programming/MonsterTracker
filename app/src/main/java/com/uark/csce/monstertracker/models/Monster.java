package com.uark.csce.monstertracker.models;

// A monster is a single instance of a Class of Monsters ("Class" in the game sense and not in the
// OOP sense). I.e. one monster object maps to one monster on the board in GH
public class Monster {
    private String Name; // Name acts as our unique identifier for a monster type
    private int Level;
    private MonsterType Type;

    // Stats. "Base" stats are given by level without modifiers from attack cards.
    private int BaseHealth;
    private int BaseMove;
    private int BaseAttack;
    private int BaseRange;

    // Non-base stats are fluid and change-turn-to-turn. A monster is defeated if its Health is 0
    private int Health;
    private int Move;
    private int Attack;
    private int Range;

    // Getters and setters
    public int getLevel() {
        return Level;
    }
    public void setLevel(int level) {
        Level = level;
    }

    public MonsterType getType() {
        return Type;
    }
    public void setType(MonsterType type) {
        Type = type;
    }

    public int getBaseHealth() {
        return BaseHealth;
    }
    public void setBaseHealth(int baseHealth) {
        BaseHealth = baseHealth;
    }

    public int getBaseMove() {
        return BaseMove;
    }
    public void setBaseMove(int baseMove) {
        BaseMove = baseMove;
    }

    public int getBaseAttack() {
        return BaseAttack;
    }
    public void setBaseAttack(int baseAttack) {
        BaseAttack = baseAttack;
    }

    public int getBaseRange() {
        return BaseRange;
    }
    public void setBaseRange(int baseRange) {
        BaseRange = baseRange;
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
}
