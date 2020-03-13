package com.example.bolasepak.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.bolasepak.activities.MainActivity;

public class StepBroadcastReceiver extends BroadcastReceiver {

    private MainActivity ma;

    public StepBroadcastReceiver(MainActivity maContext) {
        ma = maContext;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ma.setStepCount();
    }
}
