package com.uark.csce.monstertracker.MainActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.uark.csce.monstertracker.R;
import com.uark.csce.monstertracker.models.MonsterRepository;

public class MainActivity extends AppCompatActivity {
    MainContract.View view;
    MainContract.Presenter presenter;
    MonsterRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter();
        view = (MainContract.View) getSupportFragmentManager().findFragmentById(R.id.mainFragmentContainerView);
        presenter.setView(view);
        repository = MonsterRepository.getInstance(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.start();
    }
}