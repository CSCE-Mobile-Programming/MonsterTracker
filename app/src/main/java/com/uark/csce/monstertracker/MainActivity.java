package com.uark.csce.monstertracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.uark.csce.monstertracker.models.MonsterRepository;
import com.uark.csce.monstertracker.util.AppExecutors;


public class MainActivity extends AppCompatActivity {

    private AppExecutors mAppExecutors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAppExecutors = new AppExecutors();

        MonsterRepository repository = MonsterRepository.getInstance(getApplicationContext());

    }
}