package com.example.vaccinespotter.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.vaccinespotter.ServiceManager;

import org.jetbrains.annotations.NotNull;

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

        return null;
    }
}
