package com.uark.csce.monstertracker.MainActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.scenario_list:
                presenter.scenarioActivityButtonClicked();
                return true;
            case R.id.wifi_sync:
                presenter.connectActivityButtonClicked();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.start();
    }
}