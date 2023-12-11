package com.example.proyectoapilogin.view_model;

import android.os.CountDownTimer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proyectoapilogin.response.RegistroResponse;
import com.example.proyectoapilogin.retrofit.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroViewModel extends ViewModel {
    private final MutableLiveData<String> registerError = new MutableLiveData<>();
    private ApiService apiService;
    private final MutableLiveData<String> registerProcess = new MutableLiveData<>();
    private final MutableLiveData<Boolean> successfulRegister = new MutableLiveData<>();

    public LiveData<String> getRegisterProcess() {
        return registerProcess;
    }

    public RegistroViewModel(ApiService apiService){
        this.apiService = apiService;
    }

    public LiveData<String> getRegisterError() {
        return registerError;
    }

    public LiveData<Boolean> getSuccessfulRegister() {
        return successfulRegister;
    }

    public void verifyRegister(String nombre, String email, String password, String passwordconfirm) {
        apiService.register(nombre, email, password, passwordconfirm).enqueue(new Callback<RegistroResponse>() {
            @Override
            public void onResponse(Call<RegistroResponse> call, Response<RegistroResponse> response) {
                if (response.body() != null) {
                    registerProcess.setValue(response.body().getProcess());
                    if (response.body().getProcess().equals("successful")) {
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
                    } else if (response.body().getProcess().equals("failed")) {
                        RegistroResponse.ErrorMessage errorMessage = response.body().getMenssage();
                        List<String> errorList = new ArrayList<>();
                        if (errorMessage.getName() != null) {
                            errorList.add("El nombre debe tener al menos 10 caracteres");
                        }
                        if (errorMessage.getEmail() != null) {
                            errorList.add("Ingrese un email válido");
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
                } else {
                    successfulRegister.setValue(false);
                    System.out.println("Error de respuesta de la API: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<RegistroResponse> call, Throwable t) {

            }
        });
    }
}