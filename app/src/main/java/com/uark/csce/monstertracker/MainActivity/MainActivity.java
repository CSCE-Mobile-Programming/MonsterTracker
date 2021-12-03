package com.uark.csce.monstertracker.MainActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.uark.csce.monstertracker.BuildConfig;
import com.uark.csce.monstertracker.R;
import com.uark.csce.monstertracker.models.MonsterRepository;

import java.util.UUID;

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

        getUserRoomCode();

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
            case R.id.draw_all:
                presenter.drawAllButtonClicked();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.pause();
    }

    private void getUserRoomCode() {

        final String PREFS_NAME = "MyPrefsFile";
        final String PREF_ROOM_CODE_KEY = "room";
        final String DOESNT_EXIST = "";

        // Get saved version code
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String savedRoomCode = prefs.getString(PREF_ROOM_CODE_KEY, DOESNT_EXIST);
        if(savedRoomCode.equals("")) {
            UUID uuid = UUID.randomUUID();
            prefs.edit().putString(PREF_ROOM_CODE_KEY, uuid.toString()).apply();
            repository.setRoomCode(uuid.toString());
        }
        else {
            repository.setRoomCode(savedRoomCode);
        }

    }

}