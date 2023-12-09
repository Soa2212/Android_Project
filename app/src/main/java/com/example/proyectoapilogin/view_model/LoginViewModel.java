package com.example.proyectoapilogin.view_model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proyectoapilogin.response.LoginResponse;
import com.example.proyectoapilogin.retrofit.ApiService;
import com.example.proyectoapilogin.views.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
    private final MutableLiveData<String> loginError = new MutableLiveData<>();
    private ApiService apiService;
    private Context context;
    public LoginViewModel(ApiService apiService, Context context){
        this.apiService = apiService;
        this.context = context;
    }
    public LiveData<String> getLoginError() {
        return loginError;
    }
    public void verifyLogin(String email, String password) {
        apiService.login(email, password).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body() != null) {
                    if (response.body().getProcess().equals("successful")) {
                        loginError.setValue("Iniciando sesión...");
                        saveTokenInSharedPreferences(response.body().getToken());
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                    } else if (response.body().getProcess().equals("failed")) {
                        loginError.setValue("Error al iniciar sesión. Verifique sus datos");
                    }
                } else {
                    System.out.println("Error de respuesta de la API: " + response.errorBody());
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
