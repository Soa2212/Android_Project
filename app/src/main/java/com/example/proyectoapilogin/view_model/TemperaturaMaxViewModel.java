package com.example.proyectoapilogin.view_model;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.CountDownTimer;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proyectoapilogin.response.CrearHabitacionResponse;
import com.example.proyectoapilogin.response.TemperaturaMaxResponse;
import com.example.proyectoapilogin.retrofit.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TemperaturaMaxViewModel extends ViewModel {
    private ApiService apiService;
    private SharedPreferences sharedPreferences;

    private final MutableLiveData<Boolean> changeTemp = new MutableLiveData<>();

    public MutableLiveData<Boolean> getChangeTemp() {
        return changeTemp;
    }

    public TemperaturaMaxViewModel(ApiService apiService, SharedPreferences sharedPreferences){
        this.apiService = apiService;
        this.sharedPreferences = sharedPreferences;
    }

    public void ajustarTemperatura(String temperatura) {
        String token = sharedPreferences.getString("token", "");
        apiService.ajustarTemp("Bearer " + token, Integer.parseInt(temperatura)).enqueue(new Callback<TemperaturaMaxResponse>() {
            @Override
            public void onResponse(Call<TemperaturaMaxResponse> call, Response<TemperaturaMaxResponse> response) {
                if (response.body() != null) {
                    if (response.body().getProcess().equals("success")) {
                        changeTemp.setValue(true);
                    } else if (response.body().getProcess().equals("failed")) {
                        changeTemp.setValue(false);
                    }
                } else {
                    changeTemp.setValue(false);
                }
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong("temperatura", Double.doubleToRawLongBits(Double.parseDouble(temperatura)));
                editor.apply();
            }

            @Override
            public void onFailure(Call<TemperaturaMaxResponse> call, Throwable t) {

            }
        });
    }
    public double loadMaxTemperature() {
        long longValue = sharedPreferences.getLong("temperatura", Double.doubleToLongBits(30.0));
        return Double.longBitsToDouble(longValue);
    }


}
