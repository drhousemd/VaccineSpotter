package com.example.vaccinespotter.worker;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.vaccinespotter.models.NotificationModel;
import com.example.vaccinespotter.notifications.VaccineNotificationManager;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class BackgroundWorker extends Worker {
    private static final String TAG = "BackgroundWorker";
    private static final int NUMBER_OF_CYCLES = 5;
    private static final int NUMBER_OF_DAYS_IN_CYCLE = 7;
    private static final String QUERY_COWIN_FAILED = "Querying the CoWin website failed";
    private static final String SEND_NOTIFICATION_FAILED = "Failed in sending the notification to user";

    private final VaccineNotificationManager mNotificationManager;

    public BackgroundWorker(
        Context context,
        WorkerParameters workerParams) {
        super(context, workerParams);
        mNotificationManager = new VaccineNotificationManager(getApplicationContext());
        mNotificationManager.registerNotifications();
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public Result doWork() {
        boolean failed = false;
        RetrofitManager retrofitManager = new RetrofitManager();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Calendar calendar = Calendar.getInstance();
        String date = dateFormat.format(calendar.getTime());
        List<NotificationModel> models = null;

        for (int cycles = 0; cycles < NUMBER_OF_CYCLES; cycles++) {
            try {
                models = retrofitManager.queryCowin(date);
                mNotificationManager.showNotifications(models);
                calendar.add(Calendar.DATE, NUMBER_OF_DAYS_IN_CYCLE);
                date = dateFormat.format(calendar.getTime());
            } catch (IOException exception) {
                Log.e(TAG, "Exception in Query Cowin", exception);
                mNotificationManager.notificationForFailure(QUERY_COWIN_FAILED, exception.toString());
                failed = true;
            } catch (Exception exception) {
                Log.e(TAG, "Exception in Sending Notification", exception);
                mNotificationManager.notificationForFailure(SEND_NOTIFICATION_FAILED, exception.toString());
                failed = true;
            }
        }

        return failed ? Result.failure() : Result.success();
    }
}
