package com.uark.csce.monstertracker.models.info;

import java.util.List;

public class AttributesInfo {
    // Pseudo-stats
    private int Shield;
    private int Target;
    private int Retaliate;
    private int Pierce;

    // Other flags
    private boolean IsFlying;
    private boolean AttackersGainDisadvantage;

    // Immunities
    private boolean IsImmuneToCurse;
    private boolean IsImmuneToDisarm;
    private boolean IsImmuneToImmobilize;
    private boolean IsImmuneToStun;
    private boolean IsImmuneToMuddle;
    private boolean IsImmuneToPoison;
    private boolean IsImmuneToWound;

    // Raw json data
    private List<String> AttributesList;

    public int getShield() {
        return Shield;
    }
    public void setShield(int shield) {
        this.Shield = shield;
    }

    public int getTarget() {
        return Target;
    }
    public void setTarget(int target) {
        this.Target = target;
    }

    public int getRetaliate() {
        return Retaliate;
    }
    public void setRetaliate(int retaliate) {
        this.Retaliate = retaliate;
    }

    public int getPierce() {
        return Pierce;
    }
    public void setPierce(int pierce) {
        this.Pierce = pierce;
    }

    public boolean isFlying() {
        return IsFlying;
    }
    public void setFlying(boolean flying) {
        IsFlying = flying;
    }

    public boolean isAttackersGainDisadvantage() {
        return AttackersGainDisadvantage;
    }
    public void setAttackersGainDisadvantage(boolean attackersGainDisadvantage) {
        this.AttackersGainDisadvantage = attackersGainDisadvantage;
    }

    public boolean isImmuneToCurse() {
        return IsImmuneToCurse;
    }
    public void setImmuneToCurse(boolean immuneToCurse) {
        IsImmuneToCurse = immuneToCurse;
    }

    public boolean isImmuneToDisarm() {
        return IsImmuneToDisarm;
    }
    public void setImmuneToDisarm(boolean immuneToDisarm) {
        IsImmuneToDisarm = immuneToDisarm;
    }

    public boolean isImmuneToImmobilize() {
        return IsImmuneToImmobilize;
    }
    public void setImmuneToImmobilize(boolean immuneToImmobilize) {
        IsImmuneToImmobilize = immuneToImmobilize;
    }

    public boolean isImmuneToStun() {
        return IsImmuneToStun;
    }
    public void setImmuneToStun(boolean immuneToStun) {
        IsImmuneToStun = immuneToStun;
    }

    public boolean isImmuneToMuddle() {
        return IsImmuneToMuddle;
    }
    public void setImmuneToMuddle(boolean immuneToMuddle) {
        IsImmuneToMuddle = immuneToMuddle;
    }

    public boolean isImmuneToPoison() {
        return IsImmuneToPoison;
    }
    public void setImmuneToPoison(boolean immuneToPoison) {
        IsImmuneToPoison = immuneToPoison;
    }

    public boolean isImmuneToWound() {
        return IsImmuneToWound;
    }
    public void setImmuneToWound(boolean immuneToWound) {
        IsImmuneToWound = immuneToWound;
    }

    public List<String> getAttributesList() {
        return AttributesList;
    }
    public void setAttributesList(List<String> attributesList) {
        this.AttributesList = attributesList;
    }
}
