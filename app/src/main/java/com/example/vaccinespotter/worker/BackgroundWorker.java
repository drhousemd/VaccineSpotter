package com.example.vaccinespotter.worker;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.vaccinespotter.notifications.VaccineNotificationManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class BackgroundWorker extends Worker {
    private static final String TAG = "BackgroundWorker";
    private static final int numberOfCycles = 5;
    private static final int numberOfDaysInCycle = 7;
    private VaccineNotificationManager notificationManager = null;
    private String queryCowinFailed = "Querying the CoWin website failed";
    private String doWorkFailed = "Executing the worker failed";

    public BackgroundWorker(
            Context context,
            WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public Result doWork() {
        try {
            notificationManager = new VaccineNotificationManager(getApplicationContext());
            notificationManager.registerNotifications();
            RetrofitManager retrofitManager = new RetrofitManager();

            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            Calendar calendar = Calendar.getInstance();
            String date = dateFormat.format(calendar.getTime());
            for (int cycles = 0; cycles <= numberOfCycles; cycles++)
            {
                if(!retrofitManager.queryCowin(notificationManager, getApplicationContext(), date)) {
                    notificationManager.notificationForFailure(queryCowinFailed);
                    return Result.failure();
                }
                calendar.add(Calendar.DATE, numberOfDaysInCycle);
                date = dateFormat.format(calendar.getTime());
            }
            return Result.success();

        } catch (Exception ex) {
            Log.e(TAG, "Exception in worker's doWork()");
            if(notificationManager != null)
                notificationManager.notificationForFailure(doWorkFailed);
            return Result.failure();
        }
    }
}
