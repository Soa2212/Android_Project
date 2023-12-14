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
    private  String token;

    public HabitacionViewModel(HabitacionRepository habitacionRepository, String token) {
        this.habitacionRepository = habitacionRepository;
        this.token = token;
    }

    public LiveData<List<Habitacion>> getHabitaciones() {
        return habitacionRepository.getHabitaciones(token);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        private final HabitacionRepository habitacionRepository;
        private  String token;

        public Factory(HabitacionRepository habitacionRepository,String token) {
            this.habitacionRepository = habitacionRepository;
            this.token = token;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(HabitacionViewModel.class)) {
                return (T) new HabitacionViewModel(habitacionRepository,token);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }

}

