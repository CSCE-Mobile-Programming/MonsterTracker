package com.uark.csce.monstertracker.models.info;

import java.util.List;

public class CardInfo {
    private int initiative;
    private boolean shuffle;
    private List<ActionInfo> action;

    public int getInitiative() {
        return initiative;
    }
    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public boolean isShuffle() {
        return shuffle;
    }
    public void setShuffle(boolean shuffle) {
        this.shuffle = shuffle;
    }

    public List<ActionInfo> getAction() {
        return action;
    }
    public void setAction(List<ActionInfo> action) {
        this.action = action;
    }
}
