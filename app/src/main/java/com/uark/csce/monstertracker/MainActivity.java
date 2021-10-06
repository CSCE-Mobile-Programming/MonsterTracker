package com.uark.csce.monstertracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;

import com.uark.csce.monstertracker.models.daos.ConditionDao;
import com.uark.csce.monstertracker.models.entities.Condition;
import com.uark.csce.monstertracker.models.MonsterDatabase;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MonsterDatabase db = Room.databaseBuilder(getApplicationContext(),
                MonsterDatabase.class, "monster-database")
                .createFromAsset("monsterdata.db")
                .build();

        ConditionDao conditionDao = db.conditionDao();
        List<Condition> conditions = conditionDao.getAll();

        Log.i("First Item", conditions.get(0).name);
    }
}