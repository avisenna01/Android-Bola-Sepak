package com.example.bolasepak.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.room.Room;

import com.example.bolasepak.R;
import com.example.bolasepak.database.StepDatabase;
import com.example.bolasepak.model.Step;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class StepSensorService extends Service implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mStepDetectorSensor;
    private String IntentAction;

    public static StepDatabase stepDatabase;

    private static final String TAG = "StepSensorService";

    IBinder mBinder = new LocalBinder();

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        super.onCreate();

        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null)
        {
            Log.d(TAG, "exist");
            mStepDetectorSensor =
                    mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            mSensorManager.registerListener(this, mStepDetectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else Log.d(TAG, "not exist");
        stepDatabase = Room.databaseBuilder(getApplicationContext(),StepDatabase.class,"stepdb").allowMainThreadQueries().build();
        IntentAction = getResources().getString(R.string.intent_action);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d(TAG, "onSensorChanged: ");
        updateDatabase();
    }

    public void updateDatabase() {
        Log.d(TAG, "updateDatabase: ");
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Step step = new Step();
        step.setDate(date);

        List<Step> steps = stepDatabase.stepDao().getTodayStep(date);

        if (steps.size()!=0) {
            step.setSteps(steps.get(0).getSteps()+1);
            stepDatabase.stepDao().updateTodayStep(step);
        }
        else {
            step.setSteps(1);
            stepDatabase.stepDao().addTodayStep(step);
        }

        Intent intent = new Intent(IntentAction);

        sendBroadcast(intent);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    public class LocalBinder extends Binder {
        public StepSensorService getStepSensorService() {
            return StepSensorService.this;
        }
    }
}
