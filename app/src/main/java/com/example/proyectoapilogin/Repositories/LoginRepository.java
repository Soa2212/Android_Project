package com.example.proyectoapilogin.Repositories;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.proyectoapilogin.response.LoginResponse;
import com.example.proyectoapilogin.retrofit.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    private final ApiService apiService;
    private final Context context;

    public LoginRepository(ApiService apiService, Context context) {
        this.apiService = apiService;
        this.context = context;
    }

    public MutableLiveData<Boolean> verifyLogin(String email, String password) {
        MutableLiveData<Boolean> loginResult = new MutableLiveData<>();

        apiService.login(email, password).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body() != null) {
                    if (response.body().getProcess().equals("successful")) {
                        saveTokenInSharedPreferences(response.body().getToken());
                        loginResult.setValue(true);
                    } else {
                        loginResult.setValue(false);
                    }
                } else {
                    loginResult.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginResult.setValue(false);
            }
        });

        return loginResult;
    }

    private void saveTokenInSharedPreferences(String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        Log.d("Token22", token);
        editor.apply();
    }
}
