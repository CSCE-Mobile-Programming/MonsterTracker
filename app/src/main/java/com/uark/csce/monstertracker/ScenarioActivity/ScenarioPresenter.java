package com.uark.csce.monstertracker.ScenarioActivity;

import com.uark.csce.monstertracker.models.MonsterRepository;
import com.uark.csce.monstertracker.models.info.Scenario;

import java.util.ArrayList;
import java.util.List;

public class ScenarioPresenter implements ScenarioContract.Presenter {
    ScenarioContract.View view;
    private MonsterRepository mRepository;
    private List<Scenario> scenarioList;

    @Override
    public void setView(ScenarioContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {

        view.setPresenter(this);
        scenarioList = new ArrayList<>();
        getScenariosFromRepository();
    }

    @Override
    public void setRepository(MonsterRepository repository) {
        mRepository = repository;
    }

    @Override
    public List<Scenario> getScenarios() {
        return scenarioList;
    }

    @Override
    public void getScenariosFromRepository() {
        scenarioList = mRepository.getScenarios();
        view.notifyScenariosLoaded();
    }
}
