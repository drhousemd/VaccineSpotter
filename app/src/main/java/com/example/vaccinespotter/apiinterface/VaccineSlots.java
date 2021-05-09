package com.example.vaccinespotter.apiinterface;

import com.example.vaccinespotter.models.Center;
import com.example.vaccinespotter.models.Centers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

import java.util.List;

public interface VaccineSlots {

    @Headers({
        "Origin: https://www.cowin.gov.in",
        "User-Agent: TeraBaap"
    })
    @GET("api/v2/appointment/sessions/public/calendarByDistrict")
    Call<Centers> getCenters(@Query("district_id") int district_id, @Query("date") String date);
}
