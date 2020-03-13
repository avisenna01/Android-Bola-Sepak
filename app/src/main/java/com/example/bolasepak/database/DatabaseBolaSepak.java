package com.example.bolasepak.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.bolasepak.model.*;

@Database(entities = {Event.class, Team.class},version = 1,exportSchema = false)
public abstract class DatabaseBolaSepak extends RoomDatabase {
    public abstract EventDao eventDao();
    public abstract TeamDao teamDao();
}
