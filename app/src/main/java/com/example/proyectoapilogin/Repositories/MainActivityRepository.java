package com.example.proyectoapilogin.Repositories;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.proyectoapilogin.response.LoginResponse;
import com.example.proyectoapilogin.retrofit.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityRepository {
    private final MutableLiveData<Boolean> logoutValid = new MutableLiveData<>();
    private ApiService apiService;

    public MainActivityRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public MutableLiveData<Boolean> getLogoutValidity() {
        return logoutValid;
    }

    public void logout(Context context) {
        Log.e("DetalleHabitacionViewModel", "Entro a eliminarHabitacion");
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        apiService.logout("Bearer " + token).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.e("DetalleHabitacionViewModel", response.body().getProcess());
                if (response.body().getProcess().equals("successful")) {
                    logoutValid.setValue(true);
                } else {
                    logoutValid.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("DetalleHabitacionViewModel", "Error en la petición");
                logoutValid.setValue(false);
            }
        });
    }
}
