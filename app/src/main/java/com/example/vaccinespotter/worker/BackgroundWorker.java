package com.example.vaccinespotter.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.example.vaccinespotter.RetrofitManager;
import com.example.vaccinespotter.VaccineNotificationManager;

import org.jetbrains.annotations.NotNull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class BackgroundWorker extends Worker {

    private static final int numberOfCycles = 5;
    private static final int numberOfDaysInCycle = 7;
    public BackgroundWorker(
            @NonNull @NotNull Context context,
            @NonNull @NotNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public Result doWork() {
        try {
            VaccineNotificationManager notificationManager = new VaccineNotificationManager(getApplicationContext());
            notificationManager.registerNotifications();
            RetrofitManager retrofitManager = new RetrofitManager();

            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            Calendar calendar = Calendar.getInstance();
            String date = dateFormat.format(calendar.getTime());
            for (int cycles = 0; cycles <= numberOfCycles; cycles++)
            {
                if(!retrofitManager.queryCowin(notificationManager, getApplicationContext(), date)) {
                   return Result.failure();
                }
                calendar.add(Calendar.DATE, numberOfDaysInCycle);
                date = dateFormat.format(calendar.getTime());
            }
            return Result.success();

        } catch (Exception ex) {
            return Result.failure();
        }
    }
}
