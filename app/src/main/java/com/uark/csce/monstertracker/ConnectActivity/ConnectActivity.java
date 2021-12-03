package com.uark.csce.monstertracker.ConnectActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.uark.csce.monstertracker.MainActivity.MainContract;
import com.uark.csce.monstertracker.R;
import com.uark.csce.monstertracker.models.MonsterRepository;

public class ConnectActivity extends AppCompatActivity {
    ConnectContract.View view;
    ConnectContract.Presenter presenter;
    MonsterRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        repository = MonsterRepository.getInstance(getApplicationContext());

        presenter = new ConnectPresenter();
        view = (ConnectContract.View) getSupportFragmentManager().findFragmentById(R.id.connectFragmentContainerView);
        presenter.setView(view);
        presenter.setRepository(repository);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.start();
    }
}