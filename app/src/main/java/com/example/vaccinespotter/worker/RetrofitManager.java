package com.example.vaccinespotter.worker;

import android.content.Context;
import android.util.Log;

import com.example.vaccinespotter.notifications.VaccineNotificationManager;
import com.example.vaccinespotter.apiinterface.VaccineSlots;
import com.example.vaccinespotter.models.Center;
import com.example.vaccinespotter.models.Centers;
import com.example.vaccinespotter.models.NotificationModel;
import com.example.vaccinespotter.models.Session;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private final String TAG = "RetrofitManager";
    private static final int age18 = 18;
    private static final int districtId = 312;
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://cdn-api.co-vin.in")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private final VaccineSlots vaccineSlots = retrofit.create(VaccineSlots.class);

    public boolean queryCowin(VaccineNotificationManager notificationManager, Context context, String date) {

        try {
            Call<Centers> call = vaccineSlots.getCenters(districtId, date);
            Response<Centers> response = call.execute();
            assert response.body() != null;
            List<Center> centers = response.body().getCenters();
            for (Center center: centers) {
                for (Session session: center.getSessions()) {
                    if(checkFor18YearsPlus(session) && isVaccineAvailable(session)) {
                        notificationManager.notifyUser(new NotificationModel(center, session));
                    }
                }
            }
            return true;
        } catch (Exception ex) {
            Log.e(TAG, "Error in querying CoWin: " + ex);
            return false;
        }
    }

    private boolean isVaccineAvailable(Session session) {
        return (session.getAvailable_capacity() != 0);
    }

    private boolean checkFor18YearsPlus(Session session) {
        return (session.getMin_age_limit() == age18);
    }
}
