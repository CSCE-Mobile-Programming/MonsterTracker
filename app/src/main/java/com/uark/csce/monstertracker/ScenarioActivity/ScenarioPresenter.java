package com.uark.csce.monstertracker.ScenarioActivity;

public class ScenarioPresenter implements ScenarioContract.Presenter {
    ScenarioContract.View view;

    @Override
    public void setView(ScenarioContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {
        view.setPresenter(this);
    }
}
