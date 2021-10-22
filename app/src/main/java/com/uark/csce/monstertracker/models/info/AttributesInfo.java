package com.uark.csce.monstertracker.models.info;

import java.util.List;

public class AttributesInfo {
    // Pseudo-stats
    private int shield;
    private int target;
    private int retaliate;
    private int pierce;

    // Other flags
    private boolean isFlying;
    private boolean attackersGainDisadvantage;

    // Immunities
    private boolean isImmuneToCurse;
    private boolean isImmuneToDisarm;
    private boolean isImmuneToImmobilize;
    private boolean isImmuneToStun;
    private boolean isImmuneToMuddle;
    private boolean isImmuneToPoison;
    private boolean isImmuneToWound;

    // Raw json data
    private List<String> attributesList;

    public int getShield() {
        return shield;
    }
    public void setShield(int shield) {
        this.shield = shield;
    }

    public int getTarget() {
        return target;
    }
    public void setTarget(int target) {
        this.target = target;
    }

    public int getRetaliate() {
        return retaliate;
    }
    public void setRetaliate(int retaliate) {
        this.retaliate = retaliate;
    }

    public int getPierce() {
        return pierce;
    }
    public void setPierce(int pierce) {
        this.pierce = pierce;
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

    public boolean isImmuneToCurse() {
        return isImmuneToCurse;
    }
    public void setImmuneToCurse(boolean immuneToCurse) {
        isImmuneToCurse = immuneToCurse;
    }

    public boolean isImmuneToDisarm() {
        return isImmuneToDisarm;
    }
    public void setImmuneToDisarm(boolean immuneToDisarm) {
        isImmuneToDisarm = immuneToDisarm;
    }

    public boolean isImmuneToImmobilize() {
        return isImmuneToImmobilize;
    }
    public void setImmuneToImmobilize(boolean immuneToImmobilize) {
        isImmuneToImmobilize = immuneToImmobilize;
    }

    public boolean isImmuneToStun() {
        return isImmuneToStun;
    }
    public void setImmuneToStun(boolean immuneToStun) {
        isImmuneToStun = immuneToStun;
    }

    public boolean isImmuneToMuddle() {
        return isImmuneToMuddle;
    }
    public void setImmuneToMuddle(boolean immuneToMuddle) {
        isImmuneToMuddle = immuneToMuddle;
    }

    public boolean isImmuneToPoison() {
        return isImmuneToPoison;
    }
    public void setImmuneToPoison(boolean immuneToPoison) {
        isImmuneToPoison = immuneToPoison;
    }

    public boolean isImmuneToWound() {
        return isImmuneToWound;
    }
    public void setImmuneToWound(boolean immuneToWound) {
        isImmuneToWound = immuneToWound;
    }

    public List<String> getAttributesList() {
        return attributesList;
    }
    public void setAttributesList(List<String> attributesList) {
        this.attributesList = attributesList;
    }
}
