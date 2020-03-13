package com.example.bolasepak.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bolasepak.model.Event;

import java.util.List;

@Dao
public interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void addEvent(Event event);

    @Query("SELECT * FROM event WHERE strAwayTeam LIKE :keyword OR strHomeTeam LIKE :keyword")
    public List<Event> searchKeyword(String keyword);

    @Update
    public void updateEvent(Event event);
}
