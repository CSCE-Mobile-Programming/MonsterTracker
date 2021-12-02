package com.uark.csce.monstertracker.models.FirebaseModels;

import com.google.firebase.database.ValueEventListener;

public class CallbackContainer {
    private ValueEventListener gameStateListener;
    private ValueEventListener monsterStateListener;
    private ValueEventListener cardStateListener;

    public ValueEventListener getGameStateListener() {
        return gameStateListener;
    }

    public void setGameStateListener(ValueEventListener gameStateListener) {
        this.gameStateListener = gameStateListener;
    }

    public ValueEventListener getMonsterStateListener() {
        return monsterStateListener;
    }

    public void setMonsterStateListener(ValueEventListener monsterStateListener) {
        this.monsterStateListener = monsterStateListener;
    }

    public ValueEventListener getCardStateListener() {
        return cardStateListener;
    }

    public void setCardStateListener(ValueEventListener cardStateListener) {
        this.cardStateListener = cardStateListener;
    }
}
