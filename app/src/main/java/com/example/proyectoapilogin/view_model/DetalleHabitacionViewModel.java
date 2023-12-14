package com.example.proyectoapilogin.view_model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyectoapilogin.model.Habitacion;
import com.example.proyectoapilogin.Repositories.DetalleHabitacionRepository;
import com.example.proyectoapilogin.retrofit.ApiService;

public class DetalleHabitacionViewModel extends ViewModel {
    private final MutableLiveData<Habitacion> habitacion = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isDeleteSuccessful = new MutableLiveData<>();
    private final DetalleHabitacionRepository repository;

    public LiveData<Habitacion> getHabitacion() {
        return habitacion;
    }

    public LiveData<Boolean> getIsDeleteSuccessful() {
        return isDeleteSuccessful;
    }

    // Constructor público para la creación del ViewModel con Factory
    public DetalleHabitacionViewModel(ApiService apiService) {
        this.repository = new DetalleHabitacionRepository(apiService);
    }

    public void fetchHabitacionById(int habitacionId) {
        habitacion.postValue(null); // Limpiar datos anteriores
        repository.fetchHabitacionById(habitacionId).observeForever(habitacion::setValue);
    }

    public void eliminarHabitacion(Context context, int habitacionId) {
        repository.eliminarHabitacion(context, habitacionId).observeForever(isDeleteSuccessful::setValue);
    }

    // Factory dentro del ViewModel para proporcionar el ViewModel
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
