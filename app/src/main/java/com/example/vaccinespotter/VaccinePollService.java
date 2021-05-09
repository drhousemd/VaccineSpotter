package com.example.vaccinespotter;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class VaccinePollService extends Service {

    // 10 Minutes in mSecs.
    private static final long POLL_DELAY = 10 * 60 * 1000;

    private Handler mHandler;

    private Runnable mServicePollRunnable = new Runnable() {
        @Override
        public void run() {
            doWork();
            mHandler.postDelayed(mServicePollRunnable, POLL_DELAY);
        }
    };

    private void doWork() {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mHandler = new Handler();
//        mHandler.post();
        return super.onStartCommand(intent, flags, startId);
    }
}
