package com.example.bolasepak.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bolasepak.R;
import com.example.bolasepak.adapters.EventAdapter;
import com.example.bolasepak.database.DatabaseBolaSepak;
import com.example.bolasepak.model.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static DatabaseBolaSepak databaseBolaSepak;
    private ArrayList<Event> mEvent;
    private EditText searchBar;
    private RequestQueue requestQueue;
    private String URLEvent;
    private Context context;
    private int gridColumnCount;
    private String keyword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridColumnCount = getResources().getInteger(R.integer.grid_column_count);
        databaseBolaSepak = Room.databaseBuilder(getApplicationContext(), DatabaseBolaSepak.class,
                "databaseBolaSepak").allowMainThreadQueries().build();
        searchBar = findViewById(R.id.search_bar);
        requestQueue = Volley.newRequestQueue(this);
        URLEvent = getResources().getString(R.string.httphost_event);
        if (isNetworkConnected()) httpGet(URLEvent+keyword);
        else searchDb(keyword);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged: "+s);
                keyword = s.toString();
                String url = URLEvent + keyword;
                if (isNetworkConnected()) httpGet(url);
                else searchDb(keyword);
            }
        });
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
    public void searchDb(String keyword){
        keyword = '%'+keyword+'%';
        mEvent = (ArrayList<Event>) MainActivity.databaseBolaSepak.eventDao().searchKeyword(keyword);
        RecyclerView();
    }
    private void httpGet(String url){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "onResponse: "+response);
                        jsonParseEvent(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error);
            }
        });
        requestQueue.add(request);
    }
    public void jsonParseEvent(JSONObject response) {
        mEvent = new ArrayList<>();
        try{
            JSONArray jsonArray = response.getJSONArray("event");
            Log.d(TAG, "jsonParse: "+jsonArray.length());
            for (int i =0; i<jsonArray.length();i++){
                JSONObject event = jsonArray.getJSONObject(i);
                String sport = (event.getString("strSport"));
                if (!sport.equalsIgnoreCase("Soccer"))
                    continue;
                String id_event = (event.getString("idEvent"));
                String id_homeTeam = (event.getString("idHomeTeam"));
                String id_awayTeam = (event.getString("idAwayTeam"));
                String strHomeTeam = event.getString("strHomeTeam");
                String strAwayTeam = event.getString("strAwayTeam");
                String homeScore = event.getString("intHomeScore");
                String awayScore = event.getString("intAwayScore");
                String homeGoalDetail = event.getString("strHomeGoalDetails").replaceAll(";", "\n\n");
                String awayGoalDetail = event.getString("strAwayGoalDetails").replaceAll(";", "\n\n");
                String date = event.getString("dateEvent");
                String city = event.getString("strCity");
                String homeShot = event.getString("intHomeShots");
                String awayShot = event.getString("intAwayShots");
                Event newEvent = new Event(id_event, id_homeTeam, id_awayTeam,
                        strHomeTeam, strAwayTeam,
                        homeScore, awayScore,
                        homeGoalDetail, awayGoalDetail,
                        date, city,
                        homeShot, awayShot);

                mEvent.add(newEvent);
                MainActivity.databaseBolaSepak.eventDao().addEvent(newEvent);
            }
        }catch (Exception err){
            Log.d(TAG, "jsonParse: "+err);
        }
        RecyclerView();
    }
    private void RecyclerView(){
        RecyclerView searchResult = findViewById(R.id.search_result);
        searchResult.setLayoutManager(new GridLayoutManager(getApplicationContext(),gridColumnCount));
        Log.d(TAG, "RecyclerView: "+gridColumnCount);
        EventAdapter adapter = new EventAdapter(getApplicationContext(),mEvent);
        searchResult.setAdapter(adapter);
        Log.d(TAG, "RecyclerView: Displayed ");
    }
}
