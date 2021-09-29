package com.uark.csce.monstertracker.Models;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.uark.csce.monstertracker.Models.Daos.ConditionDao;
import com.uark.csce.monstertracker.Models.Entites.Condition;

@Database(entities = {Condition.class},version = 1)
public abstract class MonsterDatabase extends RoomDatabase {
    public abstract ConditionDao conditionDao();
}
