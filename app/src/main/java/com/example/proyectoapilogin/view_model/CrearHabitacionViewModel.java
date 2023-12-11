package com.example.proyectoapilogin.view_model;

import android.content.SharedPreferences;
import android.os.CountDownTimer;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proyectoapilogin.response.CrearHabitacionResponse;
import com.example.proyectoapilogin.retrofit.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearHabitacionViewModel extends ViewModel {
    private final MutableLiveData<String> roomError = new MutableLiveData<>();
    private ApiService apiService;
    private SharedPreferences sharedPreferences;
    private final MutableLiveData<Boolean> finishActivity = new MutableLiveData<>();
    private final MutableLiveData<Boolean> changeMessage = new MutableLiveData<>();

    public MutableLiveData<Boolean> getChangeMessage() {
        return changeMessage;
    }

    public MutableLiveData<Boolean> getFinishActivity() {
        return finishActivity;
    }

    public MutableLiveData<String> getRoomError() {
        return roomError;
    }

    public CrearHabitacionViewModel(ApiService apiService, SharedPreferences sharedPreferences){
        this.apiService = apiService;
        this.sharedPreferences = sharedPreferences;
    }

    public void crearHabitacion(String name) {
        String token = sharedPreferences.getString("token", "");
        apiService.storeRoom("Bearer " + token, name).enqueue(new Callback<CrearHabitacionResponse>() {
            @Override
            public void onResponse(Call<CrearHabitacionResponse> call, Response<CrearHabitacionResponse> response) {
                if (response.body() != null) {
                    if (response.body().getProcess().equals("success")) {
                        changeMessage.setValue(true);
                        new CountDownTimer(3000, 1000) {
                            public void onFinish() {
                                finishActivity.setValue(true);
                            }
                            public void onTick(long millisUntilFinished) {
                                roomError.setValue("Habitacion creada correctamente\n" +  "Volviendo al inicio en " + millisUntilFinished / 1000 + " segundos");
                            }
                        }.start();
                    } else if (response.body().getProcess().equals("failed")) {
                        changeMessage.setValue(false);
                        roomError.setValue("El nombre debe tener al menos 6 caracteres");
                    }
                } else {
                    changeMessage.setValue(false);
                    System.out.println("Error de respuesta de la API: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<CrearHabitacionResponse> call, Throwable t) {

            }
        });
    }
}