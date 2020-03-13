package com.example.bolasepak.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bolasepak.model.Step;

import java.util.List;

@Dao
public interface StepDao {

    @Insert
    public void addTodayStep(Step step);

    @Query("SELECT * FROM step WHERE date = :date")
    public List<Step> getTodayStep(String date);

    @Update
    public void updateTodayStep(Step step);
}
