package com.example.bolasepak.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "team")
public class Team {
    @PrimaryKey
    @ColumnInfo(name = "id_team")
    @NonNull
    private String id_team;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "urlPict")
    private String urlPict;
    @ColumnInfo(name = "description")
    private String description;

    public Team(@NonNull String id_team, String name, String urlPict, String description) {
        this.id_team = id_team;
        this.name = name;
        this.urlPict = urlPict;
        this.description = description;
    }

    @NonNull
    public String getId_team() {
        return id_team;
    }

    public void setId_team(@NonNull String id_team) {
        this.id_team = id_team;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlPict() {
        return urlPict;
    }

    public void setUrlPict(String urlPict) {
        this.urlPict = urlPict;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
