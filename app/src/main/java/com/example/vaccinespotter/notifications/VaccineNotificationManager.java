package com.example.vaccinespotter.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.example.vaccinespotter.R;
import com.example.vaccinespotter.models.NotificationModel;
import java.util.ArrayList;
import java.util.List;

public class VaccineNotificationManager {

    private final static int MAX_CHARACTERS_EXCEPTION_STRING = 100;
    private final String VACCINE_AVAILABLE = "Vaccine available";
    private final String TAG = "VaccineNotificationManager";
    private int mNotificationId;
    private final Context mContext;
    private List<NotificationModel> sentNotifications;
    private boolean mRegisteredNotification = false;

    public VaccineNotificationManager(Context context) {
        mContext = context;
        sentNotifications = new ArrayList<>();
    }

    public void registerNotifications() {
        if (!mRegisteredNotification) {
            NotificationHelper.createNotificationChannel(mContext,
                NotificationManagerCompat.IMPORTANCE_DEFAULT, false,
                mContext.getString(R.string.app_name), "Vaccine Spotter App notification channel.");
            mRegisteredNotification = true;
        }
    }

    public void showNotifications(List<NotificationModel> models) {
        if (models == null)
            return;

        for (NotificationModel model : models) {
            notifyUser(model);
        }
    }

    public void notifyUser(NotificationModel model) {
        if (sentNotifications.contains(model)) {
            Log.d(TAG, String.format("Notification already sent for Center name %s and session %s ", model.getCenterDetails().getName(), model.getSession().getDate().toString()));
            return;
        }

        sentNotifications.add(model);
        createNotification(model.getNotificationText());
    }

    private void createNotification(String text) {
        String channelId = NotificationHelper.getChannelId(mContext);
        Notification notification = new NotificationCompat.Builder(mContext, channelId)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(VACCINE_AVAILABLE)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setStyle(new NotificationCompat.BigTextStyle()
                .bigText(text))
            .setAutoCancel(false)
            .build();

        mContext.getSystemService(NotificationManager.class).notify(VACCINE_AVAILABLE, mNotificationId++, notification);
    }

    public void notificationForFailure(String failureText, String exception) {
        String channelId = NotificationHelper.getChannelId(mContext);
        exception = exception.substring(0, MAX_CHARACTERS_EXCEPTION_STRING);

        Notification notification = new NotificationCompat.Builder(mContext, channelId)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(failureText)
                .setContentText(exception)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(failureText))
                .setAutoCancel(false)
                .build();

        mContext.getSystemService(NotificationManager.class).notify(failureText, mNotificationId++, notification);
    }
}

