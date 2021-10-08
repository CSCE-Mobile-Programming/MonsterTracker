package com.uark.csce.monstertracker.ConnectActivity;

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
}
