package com.example.vaccinespotter.worker;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.vaccinespotter.models.NotificationModel;
import com.example.vaccinespotter.notifications.VaccineNotificationManager;
import com.example.vaccinespotter.requirement.AnyVaxAvailable18PlusRequirement;
import com.example.vaccinespotter.requirement.Requirement;
import com.example.vaccinespotter.requirement.CoviShieldAvailable45PlusRequirement;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class BackgroundWorker extends Worker {
    private static final String TAG = "BackgroundWorker";
    private static final int WEEKS_FOR_18_PLUS = 7;
    private static final int WEEKS_FOR_45_PLUS = 3;
    private static final int NUMBER_OF_DAYS_IN_CYCLE = 7;
    private static final String QUERY_COWIN_FAILED = "Querying the CoWin website failed";
    private static final String SEND_NOTIFICATION_FAILED = "Failed in sending the notification to user";

    private final VaccineNotificationManager mNotificationManager;
    private final ArrayList<Requirement> requirements;

    public BackgroundWorker(
        Context context,
        WorkerParameters workerParams) {
        super(context, workerParams);
        mNotificationManager = new VaccineNotificationManager(getApplicationContext());
        mNotificationManager.registerNotifications();
        requirements = new ArrayList<>();

        requirements.add(new AnyVaxAvailable18PlusRequirement(WEEKS_FOR_18_PLUS));
        requirements.add(new CoviShieldAvailable45PlusRequirement(WEEKS_FOR_45_PLUS));
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public Result doWork() {
        boolean failed = false;
        mNotificationManager.loadData();
        RetrofitManager retrofitManager = new RetrofitManager();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Calendar calendar = Calendar.getInstance();
        String date = dateFormat.format(calendar.getTime());
        List<NotificationModel> models = null;

        int range = 0;

        // Extract max range out of all requirements.
        for (Requirement requirement : requirements) {
            range = requirement.getRangeForSearchLimit() > range ? requirement.getRangeForSearchLimit() : range;
        }

        for (int cycle = 0; cycle < range; cycle++) {
            try {
                models = retrofitManager.queryCowin(date);

                for (Requirement requirement : requirements) {
                    // Check if this range is valid for current requirement.
                    if (cycle <= requirement.getRangeForSearchLimit()) {
                        for (NotificationModel model : models) {
                            if (requirement.isRequirementSatisfied(model)) {
                                mNotificationManager.notifyUser(model);
                            }
                        }
                    }
                }

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

        mNotificationManager.storeData();

        return failed ? Result.failure() : Result.success();
    }
}