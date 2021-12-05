package com.uark.csce.monstertracker.MainActivity;

import android.util.Log;

import com.uark.csce.monstertracker.models.FirebaseModels.FirebaseContract;
import com.uark.csce.monstertracker.models.FirebaseModels.MonsterState;
import com.uark.csce.monstertracker.models.Monster;
import com.uark.csce.monstertracker.models.MonsterRepository;
import com.uark.csce.monstertracker.models.info.CardInfo;
import com.uark.csce.monstertracker.models.info.MonsterInfo;
import com.uark.csce.monstertracker.models.info.Scenario;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class MainPresenter implements MainContract.Presenter, FirebaseContract.FirebaseCallback {
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
        repository.registerCallback("mainActivity", "", this);
    }

    @Override
    public void pause() {
        repository.unregisterCallback("mainActivity");
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
    }

    @Override
    public void scenarioActivityResult(String scenarioName) {
        repository.clearInstanceData();
        Log.i("Scenario Chosen: ", scenarioName);
        Scenario chosen = repository.getScenario(scenarioName);

        for (int i = 0; i < chosen.getMonsters().size(); i++) {
            repository.addMonsterInfo(chosen.getMonsters().get(i));
        }
    }

    @Override
    public List<MonsterInfo> getAllMonsterInfos() {
        return repository.getMonsterInfos();
    }

    @Override
    public void drawAllButtonClicked() {
        repository.drawAll();
    }

    @Override
    public void notifyGameStateChanged(Map<String, MonsterState> gameState) {
        List<MainAdapterModel> models = new ArrayList<>();
        for(String key : gameState.keySet()){
            MonsterState state = gameState.get(key);

            MainAdapterModel model = new MainAdapterModel();
            model.setInitiative(state.getCardState().getCurrentCard().getInitiative());
            model.setInfo(repository.getMonsterInfo(key));

            int numMonsters = state.getMonsters().size();

            model.setNumMonsters(numMonsters);

            models.add(model);
        }

        models.sort(new Comparator<MainAdapterModel>() {
            @Override
            public int compare(MainAdapterModel a, MainAdapterModel b) {
                return a.getInitiative() < b.getInitiative() ? -1 : (a.getInitiative() == b.getInitiative() ? 0 : 1);
            }
        });

        view.monsterInfoChanged(models);
    }

    @Override
    public void notifyMonsterStateChanged(List<Monster> monsters) {

    }

    @Override
    public void notifyCardStateChanged(CardInfo card) {

    }

    @Override
    public void removeMonster(String monsterInfoName) {
        repository.deleteMonsterInfo(monsterInfoName);
    }
}
