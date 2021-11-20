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
        view.notifyLoadDataSet();
    }

    @Override
    public void scenarioActivityButtonClicked() { view.startScenarioActivity(); }

    @Override
    public void connectActivityButtonClicked() {
        view.startConnectActivity();
    }

    @Override
    public void addMonsterButtonClicked() {
        view.showMonsterPicker();
    }

    @Override
    public void monsterPickerReturned(String monsterName) {
        repository.addMonsterInfo(monsterName);
        view.notifyLoadDataSet();
    }

    @Override
    public void scenarioActivityResult(String scenarioName) {
        repository.clearInstanceData();
        Log.i("Scenario Chosen: ", scenarioName);
        Scenario chosen = repository.getScenario(scenarioName);

        for (int i = 0; i < chosen.getMonsters().size(); i++) {
            repository.addMonsterInfo(chosen.getMonsters().get(i));
        }

        view.notifyLoadDataSet();
    }

    @Override
    public int getMonsterCount(String monsterInfoName) {
        return repository.getMonsterCount(monsterInfoName);
    }

    @Override
    public int getMonsterInitiative(String monsterInfoName) {
        return repository.getCurrentCard(monsterInfoName).getInitiative();
    }

    @Override
    public List<MonsterInfo> getSelectedMonsterInfos() {
        return repository.getSelectedMonsters();
    }

    @Override
    public List<MonsterInfo> getAllMonsterInfos() {
        return repository.getMonsterInfos();
    }

    @Override
    public void drawAllButtonClicked() {
        List<MonsterInfo> infos = repository.getSelectedMonsters();
        for(int i = 0; i<infos.size(); i++)
        {
            repository.drawNextCard(infos.get(i).getName());
        }
        view.notifyLoadDataSet();
    }
}
