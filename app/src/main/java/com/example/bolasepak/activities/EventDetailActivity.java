package com.example.bolasepak.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.bolasepak.R;
import com.example.bolasepak.model.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EventDetailActivity extends AppCompatActivity {
    ArrayList<Team> mteam;
    TextView homeTeam;
    TextView awayTeam;
    TextView scoreHome;
    TextView scoreAway;
    ImageButton homePhoto;
    ImageButton awayPhoto;
    TextView shotsHome;
    TextView shotsAway;
    TextView goalsHome;
    TextView goalsAway;
    Event event;
    TextView date;
    RequestQueue requestQueue;
    private static final String TAG = "EventDetailActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_detail);
        requestQueue = Volley.newRequestQueue(this);
        date = findViewById(R.id.date);
        homeTeam = findViewById(R.id.homeTeamName);
        awayTeam = findViewById(R.id.awayTeamName);
        scoreHome = findViewById(R.id.homeScore);
        scoreAway = findViewById(R.id.awayScore);
        homePhoto = findViewById(R.id.homeTeamPhoto);
        awayPhoto = findViewById(R.id.awayTeamPhoto);
        shotsHome = findViewById(R.id.homeShot);
        shotsAway = findViewById(R.id.awayShot);
        goalsHome = findViewById(R.id.homeGoal);
        goalsAway = findViewById(R.id.awayGoal);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        event = (Event) bundle.getSerializable("Schedule");
        Log.d(TAG, "onCreate activiety 2"+event.getDate()+date);
        assert event != null;
        date.setText(event.getDate());
        homeTeam.setText(event.getStrHomeTeam());
        awayTeam.setText(event.getStrAwayTeam());
        scoreHome.setText(event.getHomeScore());
        scoreAway.setText(event.getAwayScore());
        shotsHome.setText(event.getHomeShot());
        shotsAway.setText(event.getAwayShot());
        goalsHome.setText(event.getHomeGoalDetail());
        goalsAway.setText(event.getAwayGoalDetail());
        loadPicture(awayPhoto,event.getId_awayTeam());
        loadPicture(homePhoto,event.getId_homeTeam());
    }
    public void loadPicture(final ImageButton imageButton, final String id_team){
        mteam = (ArrayList<Team>) MainActivity.databaseBolaSepak.teamDao().searchTeamById(id_team);
        Log.d(TAG, "onCreate: "+mteam.isEmpty());
        if (!mteam.isEmpty()){
            Log.d(TAG, "onCreate: tidak ada inet, database Ada");
            if (isNetworkAvailable())
                Glide.with(this)
                        .asBitmap()
                        .load(mteam.get(0).getUrlPict())
                        .into(imageButton);
        }else {
            Glide.with(this).load(getResources().getIdentifier("loading","drawable", this.getPackageName())).into(imageButton);
            if (isNetworkAvailable())
                getPicture(this,imageButton,id_team);
        }
    }
    public void getPicture(final Context context, final ImageButton imageButton, final String id_team){
        String url = "https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id="+id_team;
        Log.d(TAG, "getPicture: "+url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("teams");
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String pictUrl = jsonObject.getString("strTeamBadge");
                            String teamName = jsonObject.getString("strTeam");
                            String description = jsonObject.getString("strLeague");
                            MainActivity.databaseBolaSepak.teamDao().addTeam(new Team((id_team),teamName,pictUrl,description));
                            Glide.with(context)
                                    .asBitmap()
                                    .load(pictUrl)
                                    .into(imageButton);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
