package com.example.vaccinespotter.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import com.example.vaccinespotter.R;

public class NotificationHelper {

    public static void createNotificationChannel(Context context, int importance, Boolean showBadge, String name, String description) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId =  getChannelId(context);

            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);
            channel.setShowBadge(showBadge);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    public static String getChannelId(Context context) {
        return context.getPackageName() + "-" + context.getString(R.string.app_name);
    }
}
