package com.example.proyectoapilogin.Repositories;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.proyectoapilogin.model.Habitacion;
import com.example.proyectoapilogin.response.HabitacionDetalleResponse;
import com.example.proyectoapilogin.response.LoginResponse;
import com.example.proyectoapilogin.retrofit.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleHabitacionRepository {
    private final ApiService apiService;

    public DetalleHabitacionRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public LiveData<Habitacion> fetchHabitacionById(int habitacionId) {
        MutableLiveData<Habitacion> habitacion = new MutableLiveData<>();

        apiService.getHabitacionById(habitacionId).enqueue(new Callback<HabitacionDetalleResponse>() {
            @Override
            public void onResponse(Call<HabitacionDetalleResponse> call, Response<HabitacionDetalleResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Habitacion habitacionObtenida = response.body().getHabitacion();
                    habitacion.setValue(habitacionObtenida);
                    Log.d("DetalleHabitacionRepository", "Petición exitosa. Habitación obtenida: " + habitacionObtenida);
                }
            }

            @Override
            public void onFailure(Call<HabitacionDetalleResponse> call, Throwable t) {
                Log.e("DetalleHabitacionRepository", "Error en la petición");
            }
        });

        return habitacion;
    }

    public LiveData<Boolean> eliminarHabitacion(Context context, int habitacionId) {
        MutableLiveData<Boolean> isDeleteSuccessful = new MutableLiveData<>();

        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        apiService.destroyRoom("Bearer " + token, habitacionId).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body() != null && response.body().getProcess().equals("success")) {
                    Log.e("DetalleHabitacionRepository", "Se eliminó la habitación");
                    isDeleteSuccessful.setValue(true);
                } else {
                    Log.e("DetalleHabitacionRepository", "No se pudo eliminar la habitación");
                    isDeleteSuccessful.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("DetalleHabitacionRepository", "Error en la petición");
                isDeleteSuccessful.setValue(false);
            }
        });

        return isDeleteSuccessful;
    }
}
