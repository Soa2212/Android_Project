package com.example.proyectoapilogin.view_model;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proyectoapilogin.response.LoginResponse;
import com.example.proyectoapilogin.retrofit.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
    private final MutableLiveData<String> loginError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> block = new MutableLiveData<>();
    private final MutableLiveData<Boolean> finishActivity = new MutableLiveData<>();
    private final MutableLiveData<Boolean> successfulLogin = new MutableLiveData<>();
    private ApiService apiService;

    public LoginViewModel(ApiService apiService){
        this.apiService = apiService;
    }

    public MutableLiveData<Boolean> getBlock() {
        return block;
    }

    public LiveData<String> getLoginError() {
        return loginError;
    }

    public LiveData<Boolean> getSuccessfulLogin() {
        return successfulLogin;
    }

    public void verifyLogin(String email, String password) {
        apiService.login(email, password).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body() != null) {
                    if (response.body().getProcess().equals("successful")) {
                        loginError.setValue("Iniciando sesión...");
                        block.setValue(true);
                        successfulLogin.setValue(true);
                    } else if (response.body().getProcess().equals("failed")) {
                        block.setValue(false);
                        loginError.setValue("Error al iniciar sesión. Verifique sus datos");
                    }
                } else {
                    block.setValue(false);
                    loginError.setValue("Error al iniciar sesión. Verifique sus datos");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }
}