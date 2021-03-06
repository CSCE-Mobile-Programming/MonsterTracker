package com.uark.csce.monstertracker.MonsterDetailsActivity;

import com.uark.csce.monstertracker.models.FirebaseModels.FirebaseContract;
import com.uark.csce.monstertracker.models.FirebaseModels.MonsterState;
import com.uark.csce.monstertracker.models.Monster;
import com.uark.csce.monstertracker.models.MonsterRepository;
import com.uark.csce.monstertracker.models.info.CardInfo;
import com.uark.csce.monstertracker.models.info.MonsterInfo;

import java.util.List;
import java.util.Map;

public class MonsterDetailsPresenter implements MonsterDetailsContract.Presenter, FirebaseContract.FirebaseCallback {
    MonsterDetailsContract.View view;
    MonsterRepository repository;

    MonsterInfo info;
    int currentLevel = 0;

    @Override
    public void setView(MonsterDetailsContract.View view) {
        this.view = view;
    }

    @Override
    public void setRepository(MonsterRepository repository) {
        this.repository = repository;
    }

    @Override
    public void start() {

        view.setPresenter(this);
        repository.registerCallback("monsterDetails", getMonsterInfo().getName(), this);
    }

    @Override
    public void pause() {
        repository.unregisterCallback("monsterDetails");
    }

    @Override
    public void setMonsterInfo(String monsterInfoName) {
        this.info = repository.getMonsterInfo(monsterInfoName);
    }

    @Override
    public MonsterInfo getMonsterInfo() {
        return this.info;
    }

    @Override
    public void addLevel() {
        if (currentLevel < 7)
            currentLevel += 1;
    }

    @Override
    public void subtractLevel() {
        if (currentLevel > 0)
            currentLevel -= 1;
    }

    @Override
    public int getLevel() {
        return this.currentLevel;
    }

    @Override
    public void addMonster(boolean isElite) {
        repository.addMonster(info.getName(), currentLevel, isElite);
    }

    @Override
    public void removeMonster(int position) {
        repository.removeMonster(info.getName(), position);
    }

    @Override
    public void addHealth(int position) {
        repository.addHealth(info.getName(), position);
    }

    @Override
    public void subtractHealth(int position) {
        repository.subtractHealth(info.getName(), position);
    }

    @Override
    public void toggleStatus(String statusName, int position) {
        repository.toggleMonsterStatus(info.getName(), statusName, position);
    }

    @Override
    public void drawCard() {
        repository.drawNextCard(info.getName());
    }

    @Override
    public void notifyGameStateChanged(Map<String, MonsterState> gameState) {

    }

    @Override
    public void notifyMonsterStateChanged(List<Monster> monsters) {
        view.monsterListChanged(monsters);
    }

    @Override
    public void notifyCardStateChanged(CardInfo card) {
        view.monsterCardChanged(card);
    }
}
