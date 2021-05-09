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
    private static final int NUMBER_OF_CYCLES = 5;
    private static final int NUMBER_OF_DAYS_IN_CYCLE = 7;
    private static final String QUERY_COWIN_FAILED = "Querying the CoWin website failed";
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
        try {
            RetrofitManager retrofitManager = new RetrofitManager();

            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            Calendar calendar = Calendar.getInstance();
            String date = dateFormat.format(calendar.getTime());
            for (int cycles = 0; cycles < NUMBER_OF_CYCLES; cycles++) {
                mNotificationManager.showNotifications(retrofitManager.queryCowin(mNotificationManager, getApplicationContext(), date));
                calendar.add(Calendar.DATE, NUMBER_OF_DAYS_IN_CYCLE);
                date = dateFormat.format(calendar.getTime());
            }
            return Result.success();

        } catch (Exception ex) {
            Log.e(TAG, "Exception in worker's doWork()" + ex);
            if(mNotificationManager != null)
                mNotificationManager.notificationForFailure(QUERY_COWIN_FAILED, ex.toString());
            return Result.failure();
        }
    }
}
