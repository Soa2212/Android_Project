package com.example.proyectoapilogin.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.example.proyectoapilogin.model.Habitacion;
import com.example.proyectoapilogin.retrofit.ApiService;
import com.example.proyectoapilogin.Repositories.HabitacionRepository;
import java.util.List;

public class HabitacionViewModel extends ViewModel {
    private final HabitacionRepository habitacionRepository;

    public HabitacionViewModel(HabitacionRepository habitacionRepository) {
        this.habitacionRepository = habitacionRepository;
    }

    public LiveData<List<Habitacion>> getHabitaciones() {
        return habitacionRepository.getHabitaciones();
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        private final HabitacionRepository habitacionRepository;

        public Factory(HabitacionRepository habitacionRepository) {
            this.habitacionRepository = habitacionRepository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(HabitacionViewModel.class)) {
                return (T) new HabitacionViewModel(habitacionRepository);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}

