package com.example.proyectoapilogin.view_model;

import android.content.SharedPreferences;
import android.os.CountDownTimer;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proyectoapilogin.Repositories.CrearHabitacionRepository;
import com.example.proyectoapilogin.retrofit.ApiService;
import com.example.proyectoapilogin.response.CrearHabitacionResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearHabitacionViewModel extends ViewModel {
    private final MutableLiveData<String> roomError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> finishActivity = new MutableLiveData<>();
    private final MutableLiveData<Boolean> changeMessage = new MutableLiveData<>();
    private final CrearHabitacionRepository repository;

    public MutableLiveData<Boolean> getChangeMessage() {
        return changeMessage;
    }

    public MutableLiveData<Boolean> getFinishActivity() {
        return finishActivity;
    }

    public MutableLiveData<String> getRoomError() {
        return roomError;
    }

    public CrearHabitacionViewModel(ApiService apiService, SharedPreferences sharedPreferences) {
        this.repository = new CrearHabitacionRepository(apiService, sharedPreferences);
    }

    public void crearHabitacion(String name) {
        repository.crearHabitacion(name).observeForever(result -> {
            if (result != null) {
                if (result.equals("Habitacion creada correctamente")) {
                    changeMessage.setValue(true);
                    new CountDownTimer(3000, 1000) {
                        public void onFinish() {
                            finishActivity.setValue(true);
                        }

                        public void onTick(long millisUntilFinished) {
                            roomError.setValue("Habitacion creada correctamente\n" + "Volviendo al inicio en " + millisUntilFinished / 1000 + " segundos");
                        }
                    }.start();
                } else {
                    changeMessage.setValue(false);
                    roomError.setValue(result);
                }
            }
        });
    }
}