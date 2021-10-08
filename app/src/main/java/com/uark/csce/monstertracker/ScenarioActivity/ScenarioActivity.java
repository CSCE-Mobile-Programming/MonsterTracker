package com.uark.csce.monstertracker.ScenarioActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.uark.csce.monstertracker.MainActivity.MainContract;
import com.uark.csce.monstertracker.MainActivity.MainPresenter;
import com.uark.csce.monstertracker.R;

public class ScenarioActivity extends AppCompatActivity {
    MainContract.View view;
    MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenario);
        presenter = new MainPresenter();
        view = (MainContract.View) getSupportFragmentManager().findFragmentById(R.id.scenarioFragmentContainerView);
        presenter.setView(view);
    }
}