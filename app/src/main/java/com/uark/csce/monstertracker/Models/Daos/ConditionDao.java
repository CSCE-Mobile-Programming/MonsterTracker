package com.uark.csce.monstertracker.Models.Daos;

import androidx.room.Dao;
import androidx.room.Query;

import com.uark.csce.monstertracker.Models.Entites.Condition;

import java.util.List;

@Dao
public interface ConditionDao {
    @Query("SELECT * FROM condition")
    List<Condition> getAll();

    @Query("SELECT * FROM condition WHERE id NOT IN (:immunityIds)")
    List<Condition> getWithoutImmunity(int[] immunityIds);
}
