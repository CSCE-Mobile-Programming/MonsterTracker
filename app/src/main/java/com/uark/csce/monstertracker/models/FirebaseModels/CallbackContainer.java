package com.uark.csce.monstertracker.models.FirebaseModels;

import com.google.firebase.database.ValueEventListener;

public class CallbackContainer {
    private ValueEventListener monsterInfoListener;
    private ValueEventListener monsterInstanceListener;
    private ValueEventListener monsterCardListener;

    public ValueEventListener getMonsterInfoListener() {
        return monsterInfoListener;
    }

    public void setMonsterInfoListener(ValueEventListener monsterInfoListener) {
        this.monsterInfoListener = monsterInfoListener;
    }

    public ValueEventListener getMonsterInstanceListener() {
        return monsterInstanceListener;
    }

    public void setMonsterInstanceListener(ValueEventListener monsterInstanceListener) {
        this.monsterInstanceListener = monsterInstanceListener;
    }

    public ValueEventListener getMonsterCardListener() {
        return monsterCardListener;
    }

    public void setMonsterCardListener(ValueEventListener monsterCardListener) {
        this.monsterCardListener = monsterCardListener;
    }
}
