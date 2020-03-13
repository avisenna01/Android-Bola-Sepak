package com.example.bolasepak.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity(tableName = "event")
public class Event implements Serializable {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id_event")
    private String id_event;
    @ColumnInfo(name = "id_homeTeam")
    private String id_homeTeam;
    @ColumnInfo(name = "id_awayTeam")
    private String id_awayTeam;
    @ColumnInfo(name = "strHomeTeam")
    private String strHomeTeam;
    @ColumnInfo(name = "strAwayTeam")
    private String strAwayTeam;
    @ColumnInfo(name = "homeScore")
    private String homeScore = "0";
    @ColumnInfo(name = "awayScore")
    private String awayScore ="0";
    @ColumnInfo(name = "homeGoalDetail")
    private String homeGoalDetail = null;
    @ColumnInfo(name = "awayGoalDetail")
    private String awayGoalDetail = null;
    @ColumnInfo(name = "weather")
    private String weather;
    @ColumnInfo(name = "date")
    private String date;
    @ColumnInfo(name = "city")
    private String city;
    @ColumnInfo(name = "homeShot")
    private String homeShot;
    @ColumnInfo(name = "awayShot")
    private String awayShot;

    public Event(@NonNull String id_event,
                 String id_homeTeam, String id_awayTeam,
                 String strHomeTeam, String strAwayTeam,
                 String homeScore, String awayScore,
                 String homeGoalDetail, String awayGoalDetail,
                 String date, String city,
                 String homeShot, String awayShot) {
        this.id_event = id_event;
        this.id_homeTeam = id_homeTeam;
        this.id_awayTeam = id_awayTeam;
        this.strHomeTeam = strHomeTeam;
        this.strAwayTeam = strAwayTeam;
        if (homeScore!="null" && awayScore!="null") {
            this.homeScore = homeScore;
            this.awayScore = awayScore;
        }
        if (homeGoalDetail=="null")
            this.homeGoalDetail = "-";
        else
            this.homeGoalDetail = homeGoalDetail;
        if (awayGoalDetail=="null")
            this.awayGoalDetail = "-";
        else
            this.awayGoalDetail = awayGoalDetail;
        this.date = date;
        this.city = city;
        if (awayShot=="null")
            this.awayShot = "-";
        else
            this.awayShot = awayShot;
        if (homeShot=="null")
            this.homeShot = "-";
        else
            this.homeShot = homeShot;
        this.weather = "-";
    }

    @NonNull
    public String getId_event() {
        return id_event;
    }

    public void setId_event(@NonNull String id_event) {
        this.id_event = id_event;
    }

    public String getId_homeTeam() {
        return id_homeTeam;
    }

    public void setId_homeTeam(String id_homeTeam) {
        this.id_homeTeam = id_homeTeam;
    }

    public String getId_awayTeam() {
        return id_awayTeam;
    }

    public void setId_awayTeam(String id_awayTeam) {
        this.id_awayTeam = id_awayTeam;
    }

    public String getStrHomeTeam() {
        return strHomeTeam;
    }

    public void setStrHomeTeam(String strHomeTeam) {
        this.strHomeTeam = strHomeTeam;
    }

    public String getStrAwayTeam() {
        return strAwayTeam;
    }

    public void setStrAwayTeam(String strAwayTeam) {
        this.strAwayTeam = strAwayTeam;
    }

    public String getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(String homeScore) {
        this.homeScore = homeScore;
    }

    public String getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(String awayScore) {
        this.awayScore = awayScore;
    }

    public String getHomeGoalDetail() {
        return homeGoalDetail;
    }

    public void setHomeGoalDetail(String homeGoalDetail) {
        this.homeGoalDetail = homeGoalDetail;
    }

    public String getAwayGoalDetail() {
        return awayGoalDetail;
    }

    public void setAwayGoalDetail(String awayGoalDetail) {
        this.awayGoalDetail = awayGoalDetail;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHomeShot() {
        return homeShot;
    }

    public void setHomeShot(String homeShot) {
        this.homeShot = homeShot;
    }

    public String getAwayShot() {
        return awayShot;
    }

    public void setAwayShot(String awayShot) {
        this.awayShot = awayShot;
    }
}
