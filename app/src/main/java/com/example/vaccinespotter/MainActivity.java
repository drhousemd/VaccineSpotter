package com.example.vaccinespotter;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.vaccinespotter.worker.BackgroundWorker;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    public static final Long REPEAT_INTERVAL = 15L;
    public static final TimeUnit REPEAT_INTERVAL_UNIT = TimeUnit.MINUTES;
    public static final String WORKER_TAG = "VaccineWorkerTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest queryWorkRequest =
                new PeriodicWorkRequest.Builder(BackgroundWorker.class, REPEAT_INTERVAL, REPEAT_INTERVAL_UNIT)
                        .addTag(WORKER_TAG)
                        .setConstraints(constraints)
                        .build();

        WorkManager.getInstance(getApplicationContext())
                .enqueueUniquePeriodicWork(
                        WORKER_TAG,
                        ExistingPeriodicWorkPolicy.KEEP,
                        queryWorkRequest);
    }
}