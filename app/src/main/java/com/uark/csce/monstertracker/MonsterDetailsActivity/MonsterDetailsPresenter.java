package com.uark.csce.monstertracker.MonsterDetailsActivity;

public class MonsterDetailsPresenter implements MonsterDetailsContract.Presenter {
    MonsterDetailsContract.View view;

    @Override
    public void setView(MonsterDetailsContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {
        view.setPresenter(this);
    }
}
