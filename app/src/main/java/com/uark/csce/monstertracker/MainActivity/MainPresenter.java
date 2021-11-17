package com.uark.csce.monstertracker.MainActivity;

import android.util.Log;

import com.uark.csce.monstertracker.models.MonsterRepository;
import com.uark.csce.monstertracker.models.info.MonsterInfo;
import com.uark.csce.monstertracker.models.info.Scenario;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View view;
    private MonsterRepository repository;

    @Override
    public void setView(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void setRepository(MonsterRepository repository) {
        this.repository = repository;
    }

    @Override
    public void start() {
        view.setPresenter(this);
    }

    @Override
    public void scenarioActivityButtonClicked() { view.startScenarioActivity(); }

    @Override
    public void scenarioActivityResult(String scenarioName) {
        Log.i("Scenario Chosen: ", scenarioName);
        Scenario chosen = repository.getScenario(scenarioName);
        List<MonsterInfo> infos = new ArrayList<MonsterInfo>();

        for (int i = 0; i < chosen.getMonsters().size(); i++) {
            infos.add(repository.getMonsterInfo(chosen.getMonsters().get(i)));
            repository.addMonsterInfo(chosen.getMonsters().get(i));
        }

        view.setupMonsterInfos(infos);
    }
}
