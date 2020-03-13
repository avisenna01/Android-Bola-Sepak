package com.example.bolasepak.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bolasepak.model.Team;

import java.util.List;

@Dao
public interface TeamDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void addTeam(Team Team);

    @Query("SELECT * FROM team WHERE id_team LIKE :keyword")
    public List<Team> searchTeamById(String keyword);

    @Update
    public void updateTeam(Team team);
}
