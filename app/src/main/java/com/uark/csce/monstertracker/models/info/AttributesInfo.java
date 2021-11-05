package com.uark.csce.monstertracker.models.info;

import android.util.Log;

import java.util.List;

public class AttributesInfo {
    // Pseudo-stats
    private int Shield;
    private int Target;
    private int Retaliate;
    private int RetaliateRange;
    private int Pierce;

    // Other flags
    private boolean IsFlying;
    private boolean AttackersGainDisadvantage;
    private boolean HasAdvantage;

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

    public int getRetaliateRange() {
        return RetaliateRange;
    }

    public void setRetaliateRange(int retaliateRange) {
        RetaliateRange = retaliateRange;
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
        for (String attribute: attributesList) {
            Log.i("AttributesInfo", attribute);
            // Special case: "Attackers gain disadvantage isn't a %-enclosed string.
            if (attribute.equals("Attackers gain Disadvantage")) {
                AttackersGainDisadvantage = true;
                continue;
            }
            // Special case: "Advantage" isn't a %-enclosed string.
            if (attribute.equals("Advantage")) {
                HasAdvantage = true;
                continue;
            }

            // shamelessly stolen from https://stackoverflow.com/questions/12595019/how-to-get-a-string-between-two-characters/12595052
            // This builds the "identifier" used in the switch statement. For 99% this is enough
            String identifier = attribute.substring(attribute.indexOf('%') + 1);
            identifier = identifier.substring(0, identifier.indexOf('%'));

            // For the other 1% (retaliate @ range) we have to do some more special handling
            if (attribute.contains("retaliate") && attribute.contains("range")) {
                // parse retaliate: Find the first thing after "% " and set it
                String retaliateVal = attribute.substring(attribute.indexOf("% ")+2, attribute.indexOf(":"));
                String rangeVal = attribute.substring(attribute.indexOf("%range% ") + 8);
                Retaliate = Integer.parseInt(retaliateVal);
                RetaliateRange = Integer.parseInt(rangeVal);
                continue;
            }


            String argumentStr = "";
            if (identifier.length() < attribute.length() - 2) {
                // There's something else in this string besides an identifier. Must be an argument.
                argumentStr = attribute.substring(attribute.indexOf("% ") + 2);
            }
            switch(identifier) {
                case "target":
                    Target = Integer.parseInt(argumentStr);
                    break;
                case "shield":
                    Shield = Integer.parseInt(argumentStr);
                    break;
                case "retaliate":
                    Retaliate = Integer.parseInt(argumentStr);
                    break;
                case "pierce":
                    Pierce = Integer.parseInt(argumentStr);
                    break;
                case "flying":
                    IsFlying = true;
                    break;
                case "curse":
                    IsImmuneToCurse = true;
                    break;
                case "immobilize":
                    IsImmuneToImmobilize = true;
                    break;
                case "disarm":
                    IsImmuneToDisarm = true;
                    break;
                case "poison":
                    IsImmuneToPoison = true;
                    break;
                case "stun":
                    IsImmuneToStun = true;
                    break;
                case "muddle":
                    IsImmuneToMuddle = true;
                    break;
                case "wound":
                    IsImmuneToWound = true;
                    break;
                default:
                    throw new IllegalArgumentException(attribute);
            }

        }
    }
}
