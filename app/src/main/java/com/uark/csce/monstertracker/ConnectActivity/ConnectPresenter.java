package com.uark.csce.monstertracker.ConnectActivity;

import android.util.Log;

import com.uark.csce.monstertracker.models.MonsterRepository;

public class ConnectPresenter implements ConnectContract.Presenter {
    ConnectContract.View view;
    private MonsterRepository repository;

    @Override
    public void setView(ConnectContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {
        view.setPresenter(this);
    }

    @Override
    public void setRepository(MonsterRepository repository) {
        this.repository = repository;
    }

    @Override
    public void joinButtonClicked() {
        view.showRoomCodePickerDialog();
    }

    @Override
    public void roomCodeSelected(String roomCode) {
        repository.setRoomCode(roomCode);
    }
}
