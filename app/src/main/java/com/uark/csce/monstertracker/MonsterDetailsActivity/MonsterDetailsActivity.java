package com.uark.csce.monstertracker.MonsterDetailsActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.uark.csce.monstertracker.R;

public class MonsterDetailsActivity extends AppCompatActivity {
    MonsterDetailsContract.Presenter presenter;
    MonsterDetailsContract.View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monster_details);
        presenter = new MonsterDetailsPresenter();
        view = (MonsterDetailsContract.View) getSupportFragmentManager().findFragmentById(R.id.monsterDetailsFragmentContainerView);
        presenter.setView(view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.start();
    }
}