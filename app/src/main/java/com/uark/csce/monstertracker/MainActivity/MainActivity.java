package com.uark.csce.monstertracker.MainActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
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
        repository = MonsterRepository.getInstance(getApplicationContext());

        presenter = new MainPresenter();
        view = (MainContract.View) getSupportFragmentManager().findFragmentById(R.id.mainFragmentContainerView);
        presenter.setView(view);
        presenter.setRepository(repository);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.start();
    }
}