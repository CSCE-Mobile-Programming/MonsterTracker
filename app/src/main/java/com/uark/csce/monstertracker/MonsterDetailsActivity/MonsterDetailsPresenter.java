package com.uark.csce.monstertracker.MonsterDetailsActivity;

import com.uark.csce.monstertracker.models.MonsterRepository;
import com.uark.csce.monstertracker.models.info.MonsterInfo;

public class MonsterDetailsPresenter implements MonsterDetailsContract.Presenter {
    MonsterDetailsContract.View view;
    MonsterRepository repository;

    MonsterInfo info;

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
}
