package com.example.vaccinespotter.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.vaccinespotter.R;
import com.example.vaccinespotter.models.NotificationModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class VaccineNotificationManager {

    private final static String FILE_NAME = "data.dat";
    private final static int MAX_CHARACTERS_EXCEPTION_STRING = 100;
    private final String VACCINE_AVAILABLE = "Vaccine available";
    private final String TAG = "VaccineNotificationManager";
    private final Context mContext;
    private int mNotificationId;
    private List<NotificationModel> mSentNotifications;
    private boolean mRegisteredNotification = false;

    public VaccineNotificationManager(Context context) {
        mContext = context;
        mSentNotifications = new ArrayList<>();
        mNotificationId = (int) System.currentTimeMillis();
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
        if (mSentNotifications.contains(model)) {
            Log.d(TAG, String.format("Notification already sent for Center name %s and session %s ", model.getCenterDetails().getName(), model.getSession().getDate().toString()));
            return;
        }

        mSentNotifications.add(model);
        createNotification(model);
    }

    public void loadData() {
        File outFile = new File(mContext.getFilesDir(), FILE_NAME);
        if (!outFile.exists()) {
            return;
        }

        ObjectInput objectInput = null;
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(outFile);
            objectInput = new ObjectInputStream(fileInputStream);
            mSentNotifications = (List<NotificationModel>) objectInput.readObject();
            objectInput.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (objectInput != null) {
                try {
                    objectInput.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    public void storeData() {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;

        try {
            File outFile = new File(mContext.getFilesDir(), FILE_NAME);
            fileOutputStream = new FileOutputStream(outFile);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(mSentNotifications);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {

            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }

            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    private void createNotification(NotificationModel model) {
        String text = model.getNotificationText();
        String title = String.format("%s for %s on %s", VACCINE_AVAILABLE, model.getSession().getMin_age_limit(), model.getSession().getDateString());
        String channelId = NotificationHelper.getChannelId(mContext);
        Notification notification = new NotificationCompat.Builder(mContext, channelId)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(title)
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
        exception = exception.length() > MAX_CHARACTERS_EXCEPTION_STRING ? exception.substring(0, MAX_CHARACTERS_EXCEPTION_STRING) : exception;

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

