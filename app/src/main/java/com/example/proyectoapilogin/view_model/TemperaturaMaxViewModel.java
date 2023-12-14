package com.example.proyectoapilogin.view_model;

import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proyectoapilogin.Repositories.TemperaturaMaxRepository;
import com.example.proyectoapilogin.retrofit.ApiService;

public class TemperaturaMaxViewModel extends ViewModel {
    private final TemperaturaMaxRepository temperaturaMaxRepository;

    public TemperaturaMaxViewModel(ApiService apiService, SharedPreferences sharedPreferences) {
        this.temperaturaMaxRepository = new TemperaturaMaxRepository(apiService, sharedPreferences);
    }

    public MutableLiveData<Boolean> getChangeTemp() {
        return temperaturaMaxRepository.getChangeTemp();
    }

    public void ajustarTemperatura(String temperatura) {
        temperaturaMaxRepository.ajustarTemperatura(temperatura);
    }

    public double loadMaxTemperature() {
        return temperaturaMaxRepository.loadMaxTemperature();
    }
}
