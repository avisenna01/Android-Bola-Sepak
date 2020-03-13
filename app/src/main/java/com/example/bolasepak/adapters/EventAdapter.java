package com.example.bolasepak.adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bolasepak.R;
import com.example.bolasepak.activities.EventDetailActivity;
import com.example.bolasepak.activities.MainActivity;
import com.example.bolasepak.model.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.NetworkInterface;
import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Event> mEvent;
    private ArrayList<Team> mTeam;
    private static final String TAG = "EventAdapter";
    private RequestOptions options;

    public EventAdapter(Context context, ArrayList<Event> mEvent) {
        Log.d(TAG, "EventAdapter: "+mEvent.isEmpty());
        this.context = context;
        this.mEvent = mEvent;
        options = new RequestOptions().centerCrop().placeholder(R.drawable.loading).error(R.drawable.loading);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.date.setText(mEvent.get(position).getDate());
        holder.awayTeamName.setText(mEvent.get(position).getStrAwayTeam());
        holder.homeTeamName.setText(mEvent.get(position).getStrHomeTeam());
        holder.awayScore.setText(mEvent.get(position).getAwayScore());
        holder.homeScore.setText(mEvent.get(position).getHomeScore());
        holder.eventLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Event event = mEvent.get(position);
                passData(event);
            }
        });
        loadPicture(holder.awayTeamPhoto,position,mEvent.get(position).getId_awayTeam());
        loadPicture(holder.homeTeamPhoto,position,mEvent.get(position).getId_homeTeam());
    }

    private void passData(Event event){
        Intent intent = new Intent(context, EventDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Schedule", event);
        intent.putExtras(bundle);
        context.startActivities(new Intent[]{intent});
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork!=null && activeNetwork.isConnectedOrConnecting();
    }

    private void loadPicture(final ImageButton imageButton, final int position, final String id_team){
        mTeam = (ArrayList<Team>) MainActivity.databaseBolaSepak.teamDao().searchTeamById(id_team);
        Log.d(TAG, "onCreate: "+mTeam.isEmpty());
        if (!mTeam.isEmpty()){
            Log.d(TAG, "onCreate: database Ada");
            if (isNetworkConnected())
                Glide.with(context)
                        .asBitmap()
                        .apply(options)
                        .load(mTeam.get(0).getUrlPict())
                        .into(imageButton);
        }else {
            Glide.with(context)
                    .load(context.getResources().getIdentifier("loading","drawable", context.getPackageName())).apply(options)
                    .into(imageButton);
            if (isNetworkConnected())
                getPicture(imageButton,position,id_team);
        }
    }

    private void getPicture(final ImageButton imageButton, final int position, final String id_team) {
        String url = "https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id="+id_team;
        Log.d(TAG, "getPicture: "+url);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
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
                                    .apply(options)
                                    .into(imageButton);
                            Log.d(TAG, "onResponse: "+pictUrl);
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
    @Override
    public int getItemCount() {
        return mEvent.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView date;
        ImageButton awayTeamPhoto;
        ImageButton homeTeamPhoto;
        TextView awayScore;
        TextView homeScore;
        TextView awayTeamName;
        TextView homeTeamName;
        LinearLayout eventLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            awayTeamName = itemView.findViewById(R.id.awayTeamName);
            homeTeamName = itemView.findViewById(R.id.homeTeamName);
            awayTeamPhoto = itemView.findViewById(R.id.awayTeamPhoto);
            homeTeamPhoto = itemView.findViewById(R.id.homeTeamPhoto);
            awayScore = itemView.findViewById(R.id.awayScore);
            homeScore = itemView.findViewById(R.id.homeScore);
//            weather = itemView.findViewById(R.id.weather);
            eventLayout = itemView.findViewById(R.id.event_card_layout);
        }
    }
}
