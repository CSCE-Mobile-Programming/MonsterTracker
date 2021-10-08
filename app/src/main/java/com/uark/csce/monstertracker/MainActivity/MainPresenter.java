package com.uark.csce.monstertracker.MainActivity;

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View view;

    @Override
    public void setView(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {
        view.setPresenter(this);
    }
}
