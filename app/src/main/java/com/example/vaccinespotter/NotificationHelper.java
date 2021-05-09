package com.example.vaccinespotter;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

public class NotificationHelper {

    public static void createNotificationChannel(Context context, int importance, Boolean showBadge, String name, String description) {
        // 1
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // 2
            String channelId =  getChannelId(context);

            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);
            channel.setShowBadge(showBadge);

            // 3
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    public static String getChannelId(Context context) {
        return context.getPackageName() + "-" + context.getString(R.string.app_name);
    }

}
