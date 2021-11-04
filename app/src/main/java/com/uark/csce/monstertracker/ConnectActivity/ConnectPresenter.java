package com.uark.csce.monstertracker.ConnectActivity;

import android.util.Log;

public class ConnectPresenter implements ConnectContract.Presenter {
    ConnectContract.View view;

    @Override
    public void setView(ConnectContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {
        view.setPresenter(this);
    }

    @Override
    public void connectButtonClicked() {
        Log.d("ConnectPresenter", "Connect button was clicked");
    }

    @Override
    public void joinButtonClicked() {
        Log.d("ConnectPresenter", "Join button was clicked");
    }
}
