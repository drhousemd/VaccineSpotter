package com.example.vaccinespotter;

import android.content.Context;
import android.widget.Toast;

import com.example.vaccinespotter.apiinterface.VaccineSlots;
import com.example.vaccinespotter.models.Center;
import com.example.vaccinespotter.models.CenterBase;
import com.example.vaccinespotter.models.Centers;
import com.example.vaccinespotter.models.NotificationModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://cdn-api.co-vin.in")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private final VaccineSlots vaccineSlots = retrofit.create(VaccineSlots.class);

    public void QueryCowin(Context context, String date) {

        VaccineNotificationManager notificationManager = new VaccineNotificationManager(context);
        notificationManager.registerNotifications();
        int districtId = 312;
        Call<Centers> call = vaccineSlots.getCenters(districtId, date);

        call.enqueue(new Callback<Centers>() {
            @Override
            public void onResponse(Call<Centers> call, Response<Centers> response) {
                if (response.isSuccessful()) {
                    // This contains the response.
                    Centers centers = response.body();
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                    Center center = centers.getCenters().get(0);
                    notificationManager.notifyUser(new NotificationModel((CenterBase) center, center.getSessions()[0]));
                    notificationManager.notifyUser(new NotificationModel((CenterBase) center, center.getSessions()[0]));

                    notificationManager.notifyUser(new NotificationModel((CenterBase) center, center.getSessions()[0]));
                }
            }

            @Override
            public void onFailure(Call<Centers> call, Throwable t) {

            }
        });
    }
}
