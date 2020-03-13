package com.example.bolasepak.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.bolasepak.model.Step;

@Database(entities = {Step.class}, version = 1)
public abstract class StepDatabase extends RoomDatabase {
    public abstract StepDao stepDao();
}
