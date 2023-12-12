package com.example.proyectoapilogin.view_model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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
    private Context context;


    public LoginViewModel(ApiService apiService, Context context) {
        this.apiService = apiService;
        this.context = context;
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
                        saveTokenInSharedPreferences(response.body().getToken());
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

    private void saveTokenInSharedPreferences(String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply();
    }

}