package com.example.proyectoapilogin.view_model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyectoapilogin.model.Habitacion;
import com.example.proyectoapilogin.response.HabitacionDetalleResponse;
import com.example.proyectoapilogin.response.LoginResponse;
import com.example.proyectoapilogin.retrofit.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleHabitacionViewModel extends ViewModel {
    private final MutableLiveData<Habitacion> habitacion = new MutableLiveData<>();
    private final ApiService apiService;
    private final MutableLiveData<Boolean> isDeleteSuccessful = new MutableLiveData<>();
    public LiveData<Boolean> getIsDeleteSuccessful() {
        return isDeleteSuccessful;
    }
    private SharedPreferences sharedPreferences;

    private DetalleHabitacionViewModel(ApiService apiService) {
        this.apiService = apiService;
    }

    public LiveData<Habitacion> getHabitacion() {
        return habitacion;
    }

    public void fetchHabitacionById(int habitacionId) {
        apiService.getHabitacionById(habitacionId).enqueue(new Callback<HabitacionDetalleResponse>() {
            @Override
            public void onResponse(@NonNull Call<HabitacionDetalleResponse> call, @NonNull Response<HabitacionDetalleResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Habitacion habitacionObtenida = response.body().getHabitacion();
                    habitacion.setValue(habitacionObtenida);
                    Log.d("DetalleHabitacionViewModel", "Petición exitosa. Habitación obtenida: " + habitacionObtenida);
                }
            }

            @Override
            public void onFailure(@NonNull Call<HabitacionDetalleResponse> call, @NonNull Throwable t) {
                Log.e("DetalleHabitacionViewModel", "Error en la petición");
            }
        });
    }

    public void eliminarHabitacion(Context context, int habitacionId) {
        Log.e("DetalleHabitacionViewModel", "Entro a eliminarHabitacion");
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        apiService.destroyRoom("Bearer " + token, habitacionId).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                Log.e("DetalleHabitacionViewModel", "Entro a onResponse");
                if (response.body().getProcess().equals("success")) {
                    Log.e("DetalleHabitacionViewModel", "Se elimino");
                    isDeleteSuccessful.setValue(true);
                }
                else {
                    Log.e("DetalleHabitacionViewModel", "No se elimino");
                    isDeleteSuccessful.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                Log.e("DetalleHabitacionViewModel", "Error en la petición");
                isDeleteSuccessful.setValue(false);
            }
        });
    }

    public static class Factory implements ViewModelProvider.Factory {
        private final ApiService apiService;

        public Factory(ApiService apiService) {
            this.apiService = apiService;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(DetalleHabitacionViewModel.class)) {
                //noinspection unchecked
                return (T) new DetalleHabitacionViewModel(apiService);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
