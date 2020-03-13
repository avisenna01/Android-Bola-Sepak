package com.example.bolasepak.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bolasepak.R;
import com.example.bolasepak.activities.MainActivity;
import com.example.bolasepak.model.*;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Event> mEvent = new ArrayList<>();

    public EventAdapter(Context context, ArrayList<Event> mEvent) {
        this.context = context;
        this.mEvent = mEvent;
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
        //TODO
    }

    private void loadPicture(final ImageView imageView,final int position, final String id_team){
        //TODO
    }

    public void getPicture(final ImageView imageView, final int position, final String id_team) {
        //TODO
    }
    @Override
    public int getItemCount() {
        return mEvent.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView date;
        ImageView awayTeamPhoto;
        ImageView homeTeamPhoto;
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
