package com.uark.csce.monstertracker.MainActivity;

import android.util.Log;

import com.uark.csce.monstertracker.models.FirebaseModels.FirebaseContract;
import com.uark.csce.monstertracker.models.FirebaseModels.MainActivityInfo;
import com.uark.csce.monstertracker.models.Monster;
import com.uark.csce.monstertracker.models.MonsterRepository;
import com.uark.csce.monstertracker.models.info.CardInfo;
import com.uark.csce.monstertracker.models.info.MonsterInfo;
import com.uark.csce.monstertracker.models.info.Scenario;

import java.util.ArrayList;
import java.util.List;

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
        // List<MonsterInfo> infos = repository.getSelectedMonsters();
        // for(int i = 0; i<infos.size(); i++)
        // {
        //     repository.drawNextCard(infos.get(i).getName());
        // }
        // view.notifyLoadDataSet();
    }

    @Override
    public void notifyMonsterInfoChanged(List<MainActivityInfo> activityInfos) {

        List<MainAdapterModel> models = new ArrayList<>();

        for(MainActivityInfo info : activityInfos)
        {
            MainAdapterModel model = new MainAdapterModel();
            model.setInfo(repository.getMonsterInfo(info.getMonsterInfoName()));
            model.setInitiative(info.getInitiative());
            model.setNumMonsters(info.getNumMonsters());

            models.add(model);
        }

        view.monsterInfoChanged(models);
    }

    @Override
    public void notifyMonsterInstanceChanged(List<Monster> monsters) {

    }

    @Override
    public void notifyMonsterCardsChanged(CardInfo currentCard) {

    }
}
