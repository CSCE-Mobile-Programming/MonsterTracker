package com.uark.csce.monstertracker.Models.Entites;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Condition {
    @PrimaryKey
    public int id;

    public String name;

    public String image;
}
