package com.example.proyectoapilogin.Repositories;

import android.os.CountDownTimer;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.proyectoapilogin.response.RegistroResponse;
import com.example.proyectoapilogin.retrofit.ApiService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroRepository {
    private final MutableLiveData<String> registerProcess = new MutableLiveData<>();
    private final MutableLiveData<String> registerError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> successfulRegister = new MutableLiveData<>();
    private ApiService apiService;

    public RegistroRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public MutableLiveData<String> getRegisterProcess() {
        return registerProcess;
    }

    public MutableLiveData<String> getRegisterError() {
        return registerError;
    }

    public MutableLiveData<Boolean> getSuccessfulRegister() {
        return successfulRegister;
    }

    public void verifyRegister(String nombre, String email, String password, String passwordconfirm) {
        Log.d("RegistroRepository", "verifyRegister called with: nombre = [" + nombre + "], email = [" + email + "], password = [not shown], passwordconfirm = [not shown]");
        apiService.register(nombre, email, password, passwordconfirm).enqueue(new Callback<RegistroResponse>() {
            @Override
            public void onResponse(@NonNull Call<RegistroResponse> call, @NonNull Response<RegistroResponse> response) {
                Log.d("RegistroRepository", "onResponse called with: call = [" + call + "], response = [" + response + "]");
                if (response.isSuccessful()) {
                    RegistroResponse registroResponse = response.body();
                    if (registroResponse != null) {
                        registerProcess.setValue(registroResponse.getProcess());
                        if (registroResponse.getProcess().equals("successful")) {
                            new CountDownTimer(5000, 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    registerError.setValue("Se ha enviado un correo de confirmación a su email. Verifiquelo e inicie sesión para registrar su cuenta.\n" +
                                            "Volviendo al inicio en: " + millisUntilFinished / 1000 + " segundos");
                                }

                                @Override
                                public void onFinish() {
                                    successfulRegister.setValue(true);
                                }
                            }.start();
                        }
                    }
                } else {
                    if (response.errorBody() != null) {
                        try {
                            RegistroResponse errorResponse = new Gson().fromJson(response.errorBody().string(), RegistroResponse.class);
                            JsonElement messageElement = errorResponse.getMessage();
                            if (messageElement.isJsonObject()) {
                                RegistroResponse.ErrorMessage errorMessage = new Gson().fromJson(messageElement, RegistroResponse.ErrorMessage.class);
                                List<String> errorList = new ArrayList<>();
                                if (errorMessage.getName() != null) {
                                    errorList.add("El nombre debe tener al menos 10 caracteres");
                                }
                                if (errorMessage.getEmail() != null) {
                                    errorList.add("El email debe ser válido");
                                }
                                if (errorMessage.getPassword() != null) {
                                    errorList.add("La contraseña debe tener al menos 6 caracteres");
                                }
                                if (errorMessage.getPasswordConfirmation() != null) {
                                    errorList.add("Las contraseñas no coinciden");
                                }
                                String errorString = String.join("\n", errorList);
                                registerError.setValue(errorString);
                                successfulRegister.setValue(false);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.d("RegistroRepository", "Error body is null");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<RegistroResponse> call, @NonNull Throwable t) {
                Log.d("RegistroRepository", "onFailure called with: call = [" + call + "], t = [" + t + "]");
            }
        });
    }
}