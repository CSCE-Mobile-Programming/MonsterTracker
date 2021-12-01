package com.uark.csce.monstertracker.models.info;

import java.util.List;

public class CardInfo {
    private int Initiative;
    private boolean Shuffle;

    public int getInitiative() {
        return Initiative;
    }
    public void setInitiative(int initiative) {
        this.Initiative = initiative;
    }

    public boolean isShuffle() {
        return Shuffle;
    }
    public void setShuffle(boolean shuffle) {
        this.Shuffle = shuffle;
    }
}
