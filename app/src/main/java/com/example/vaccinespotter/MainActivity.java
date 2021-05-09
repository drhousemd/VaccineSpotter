package com.example.vaccinespotter;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.example.vaccinespotter.worker.BackgroundWorker;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    public static Long REPEAT_INTERVAL = 15L;
    public static TimeUnit REPEAT_INTERVAL_UNIT = TimeUnit.MINUTES;
    public static String workerTag = "VaccineWorkerTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PeriodicWorkRequest queryWorkRequest =
                new PeriodicWorkRequest.Builder(BackgroundWorker.class, REPEAT_INTERVAL, REPEAT_INTERVAL_UNIT)
                        .addTag(workerTag)
                        .build();

        WorkManager.getInstance(getApplicationContext())
                .enqueueUniquePeriodicWork(
                        workerTag,
                        ExistingPeriodicWorkPolicy.KEEP,
                        queryWorkRequest);
    }
}