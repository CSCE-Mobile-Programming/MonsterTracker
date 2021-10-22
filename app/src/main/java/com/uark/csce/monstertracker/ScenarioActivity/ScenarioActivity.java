package com.uark.csce.monstertracker.ScenarioActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.uark.csce.monstertracker.MainActivity.MainContract;
import com.uark.csce.monstertracker.MainActivity.MainPresenter;
import com.uark.csce.monstertracker.R;
import com.uark.csce.monstertracker.models.MonsterRepository;

public class ScenarioActivity extends AppCompatActivity {
    ScenarioContract.View view;
    ScenarioContract.Presenter presenter;
    MonsterRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenario);

        repository = MonsterRepository.getInstance(getApplicationContext());

        presenter = new ScenarioPresenter();
        presenter.setRepository(repository);

        view = (ScenarioContract.View) getSupportFragmentManager().findFragmentById(R.id.scenarioFragmentContainerView);

        presenter.setView(view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.start();
    }
}