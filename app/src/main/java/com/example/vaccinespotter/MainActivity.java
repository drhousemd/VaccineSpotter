package com.example.vaccinespotter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.vaccinespotter.apiinterface.VaccineSlots;
import com.example.vaccinespotter.models.Center;
import com.example.vaccinespotter.models.Centers;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://cdn-api.co-vin.in")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        VaccineSlots vaccineSlots = retrofit.create(VaccineSlots.class);

        Call<Centers> call = vaccineSlots.getCenters(312, "15-05-2021");

        call.enqueue(new Callback<Centers>() {
            @Override
            public void onResponse(Call<Centers> call, Response<Centers> response) {
                if (response.isSuccessful()) {
                    // This contains the response.
                    Centers centers = response.body();
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Centers> call, Throwable t) {

            }
        });
    }
}