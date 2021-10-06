package com.uark.csce.monstertracker.models;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.uark.csce.monstertracker.models.daos.ConditionDao;
import com.uark.csce.monstertracker.models.entities.Condition;

@Database(entities = {Condition.class},version = 1)
public abstract class MonsterDatabase extends RoomDatabase {
    public abstract ConditionDao conditionDao();
}
