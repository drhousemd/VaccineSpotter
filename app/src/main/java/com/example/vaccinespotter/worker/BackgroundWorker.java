package com.example.vaccinespotter.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.example.vaccinespotter.RetrofitManager;
import org.jetbrains.annotations.NotNull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class BackgroundWorker extends Worker {

    public BackgroundWorker(
            @NonNull @NotNull Context context,
            @NonNull @NotNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public Result doWork() {
        RetrofitManager retrofitManager = new RetrofitManager();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Calendar calendar = Calendar.getInstance();
        String date = dateFormat.format(calendar.getTime());
        for(int cycles = 0; cycles <= 5 ;cycles++)
        {
            retrofitManager.QueryCowin(getApplicationContext(), date);
            calendar.add(Calendar.DATE, 7);
            date = dateFormat.format(calendar.getTime());
        }
        return Result.success();
    }
}
