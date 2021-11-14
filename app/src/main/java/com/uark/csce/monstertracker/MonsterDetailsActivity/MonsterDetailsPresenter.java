package com.uark.csce.monstertracker.MonsterDetailsActivity;

import com.uark.csce.monstertracker.models.MonsterRepository;
import com.uark.csce.monstertracker.models.info.MonsterInfo;

public class MonsterDetailsPresenter implements MonsterDetailsContract.Presenter {
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
}
