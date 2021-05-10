package com.example.vaccinespotter.worker;

import android.content.Context;

import com.example.vaccinespotter.apiinterface.VaccineSlots;
import com.example.vaccinespotter.models.Center;
import com.example.vaccinespotter.models.Centers;
import com.example.vaccinespotter.models.NotificationModel;
import com.example.vaccinespotter.models.Session;
import com.example.vaccinespotter.notifications.VaccineNotificationManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private static final String TAG = "RetrofitManager";
    private static final int DISTRICT_ID = 312;

    private final Retrofit mRetrofit = new Retrofit.Builder()
        .baseUrl("https://cdn-api.co-vin.in")
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    private final VaccineSlots mVaccineSlots = mRetrofit.create(VaccineSlots.class);

    public List<NotificationModel> queryCowin(String date) throws IOException {
        List<NotificationModel> notificationModels = new ArrayList<NotificationModel>();
        Call<Centers> call = mVaccineSlots.getCenters(DISTRICT_ID, date);
        Response<Centers> response = call.execute();
        assert response.body() != null;

        for (Center center : response.body().getCenters()) {
            for (Session session : center.getSessions()) {
                notificationModels.add(new NotificationModel(center, session));
            }
        }

        return notificationModels;
    }
}
