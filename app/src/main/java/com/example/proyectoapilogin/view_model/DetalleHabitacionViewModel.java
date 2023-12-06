package com.example.proyectoapilogin.view_model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyectoapilogin.model.Habitacion;
import com.example.proyectoapilogin.response.HabitacionDetalleResponse;
import com.example.proyectoapilogin.retrofit.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleHabitacionViewModel extends ViewModel {
    private final MutableLiveData<Habitacion> habitacion = new MutableLiveData<>();
    private final ApiService apiService;

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
