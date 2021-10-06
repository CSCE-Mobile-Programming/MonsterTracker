package com.uark.csce.monstertracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;

import com.uark.csce.monstertracker.models.daos.ConditionDao;
import com.uark.csce.monstertracker.models.entities.Condition;
import com.uark.csce.monstertracker.models.MonsterDatabase;
import com.uark.csce.monstertracker.util.AppExecutors;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppExecutors mAppExecutors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAppExecutors = new AppExecutors();

        MonsterDatabase db = Room.databaseBuilder(getApplicationContext(),
                MonsterDatabase.class, "monsterdata").build();

        ConditionDao conditionDao = db.conditionDao();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                List<Condition> conditions = conditionDao.getAll();
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }
}