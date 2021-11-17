package com.uark.csce.monstertracker.MonsterDetailsActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.uark.csce.monstertracker.R;
import com.uark.csce.monstertracker.models.Monster;
import com.uark.csce.monstertracker.models.MonsterRepository;

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
        MonsterRepository repository = MonsterRepository.getInstance(this);
        presenter.setRepository(repository);

        setupMonsterInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.start();
    }

    private void setupMonsterInfo() {
        Intent startingIntent = getIntent();
        if (startingIntent != null) {
            presenter.setMonsterInfo(startingIntent.getStringExtra("monsterName"));
        }
    }
}