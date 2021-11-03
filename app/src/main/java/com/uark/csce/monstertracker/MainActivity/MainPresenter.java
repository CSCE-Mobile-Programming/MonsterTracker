package com.uark.csce.monstertracker.MainActivity;

import android.util.Log;

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

    @Override
    public void scenarioActivityButtonClicked() { view.startScenarioActivity(); }

    @Override
    public void scenarioActivityResult(String scenario) {
        Log.i("Scenario Chosen: ", scenario);
    }
}
