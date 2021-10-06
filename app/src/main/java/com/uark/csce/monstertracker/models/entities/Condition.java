package com.uark.csce.monstertracker.models.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Condition {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;

    public String image;
}
