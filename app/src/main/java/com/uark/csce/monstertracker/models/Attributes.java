package com.uark.csce.monstertracker.models;

public class Attributes {
    private int shield;
    private int pierce;
    private int retaliate;
    private int target;

    private boolean isFlying;
    private boolean attackersGainDisadvantage;

    private boolean isStrengthened;
    private boolean isDisarmed;
    private boolean isImmobilized;
    private boolean isInvisible;
    private boolean isStunned;
    private boolean isMuddled;
    private boolean isPoisoned;
    private boolean isWounded;

    public int getShield() {
        return shield;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }

    public int getPierce() {
        return pierce;
    }

    public void setPierce(int pierce) {
        this.pierce = pierce;
    }

    public int getRetaliate() {
        return retaliate;
    }

    public void setRetaliate(int retaliate) {
        this.retaliate = retaliate;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public boolean isFlying() {
        return isFlying;
    }

    public void setFlying(boolean flying) {
        isFlying = flying;
    }

    public boolean isAttackersGainDisadvantage() {
        return attackersGainDisadvantage;
    }

    public void setAttackersGainDisadvantage(boolean attackersGainDisadvantage) {
        this.attackersGainDisadvantage = attackersGainDisadvantage;
    }

    public boolean isStrengthened() {
        return isStrengthened;
    }

    public void setStrengthened(boolean strengthened) {
        isStrengthened = strengthened;
    }

    public boolean isInvisible() {
        return isInvisible;
    }

    public void setInvisible(boolean invisible) {
        isInvisible = invisible;
    }

    public boolean isDisarmed() {
        return isDisarmed;
    }

    public void setDisarmed(boolean disarmed) {
        isDisarmed = disarmed;
    }

    public boolean isImmobilized() {
        return isImmobilized;
    }

    public void setImmobilized(boolean immobilized) {
        isImmobilized = immobilized;
    }

    public boolean isStunned() {
        return isStunned;
    }

    public void setStunned(boolean stunned) {
        isStunned = stunned;
    }

    public boolean isMuddled() {
        return isMuddled;
    }

    public void setMuddled(boolean muddled) {
        isMuddled = muddled;
    }

    public boolean isPoisoned() {
        return isPoisoned;
    }

    public void setPoisoned(boolean poisoned) {
        isPoisoned = poisoned;
    }

    public boolean isWounded() {
        return isWounded;
    }

    public void setWounded(boolean wounded) {
        isWounded = wounded;
    }
}
