package com.example.proyectoapilogin.view_model;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyectoapilogin.model.Habitacion;
import com.example.proyectoapilogin.response.HabitacionResponse;
import com.example.proyectoapilogin.retrofit.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HabitacionViewModel extends ViewModel {
    private MutableLiveData<List<Habitacion>> habitaciones = new MutableLiveData<>();
    private ApiService apiService;

    // Constructor público
    public HabitacionViewModel(ApiService apiService) {
        this.apiService = apiService;
        fetchHabitaciones();
    }

    // Método para obtener las/la habitaciones como LiveData
    public LiveData<List<Habitacion>> getHabitaciones() {
        return habitaciones;
    }

    // Método para realizar la petición a la API y actualizar el LiveData
    private void fetchHabitaciones() {
        apiService.getHabitaciones().enqueue(new Callback<HabitacionResponse>() {
            @Override
            public void onResponse(Call<HabitacionResponse> call, Response<HabitacionResponse> response) {
                if (response.isSuccessful()) {
                    habitaciones.setValue(response.body().getHabitaciones());
                    Log.d("HabitacionViewModel", "Petición exitosa. Habitaciones obtenidas: " + response.body().getHabitaciones());
                }
                else {
                    Log.e("HabitacionViewModel", "Error en la petición: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<HabitacionResponse> call, Throwable t) {
                Log.e("HabitacionViewModel", "Error en la petición: " + t.getMessage());
                t.printStackTrace();
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
            if (modelClass.isAssignableFrom(HabitacionViewModel.class)) {
                //noinspection unchecked
                return (T) new HabitacionViewModel(apiService);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
