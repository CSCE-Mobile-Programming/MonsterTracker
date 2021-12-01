package com.uark.csce.monstertracker.MonsterDetailsActivity;

import androidx.annotation.NonNull;

import com.uark.csce.monstertracker.models.Monster;
import com.uark.csce.monstertracker.models.MonsterRepository;
import com.uark.csce.monstertracker.models.info.CardInfo;
import com.uark.csce.monstertracker.models.info.MonsterInfo;

import java.util.List;

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

    @Override
    public Monster getMonster(int position) {
        List<Monster> monsters = repository.getMonsters(info.getName());
        return monsters.get(position);
    }

    @Override
    public int getMonsterCount() {
        return repository.getMonsters(info.getName()).size();
    }

    @Override
    public void addMonster(boolean isElite) {
        repository.addMonster(info.getName(), currentLevel, isElite);
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
    public void toggleStatus(String statusName, int position, @NonNull MonsterDetailsContract.ToggleStatusCallback callback) {
        boolean status = repository.toggleMonsterStatus(info.getName(), statusName, position);
        callback.onToggleStatus(status);
    }

    @Override
    public void drawCard(@NonNull MonsterDetailsContract.GetCardCallback callback) {
        CardInfo cardInfo = repository.drawNextCard(info.getName());
        callback.onCardReceived(cardInfo);
    }

    @Override
    public void getCurrentCardInfo(MonsterDetailsContract.GetCardCallback callback) {
        CardInfo cardInfo = repository.getCurrentCard(info.getName());
        callback.onCardReceived(cardInfo);
    }
}
