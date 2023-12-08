package com.example.proyectoapilogin.view_model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyectoapilogin.model.Token;
import com.example.proyectoapilogin.response.TokenResponse;
import com.example.proyectoapilogin.retrofit.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends ViewModel {
    private MutableLiveData<Boolean> isTokenValid = new MutableLiveData<>();
    private ApiService apiService;

    public MainActivityViewModel(ApiService apiService) {
        this.apiService = apiService;
    }

    public LiveData<Boolean> getTokenValidity() {
        return isTokenValid;
    }

    public void verifyToken(String token) {
        apiService.verificarToken(token).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()) {
                    isTokenValid.setValue(response.body().getMessage());
                } else {
                    isTokenValid.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                isTokenValid.setValue(false);
            }
        });
    }

    public static class Factory implements ViewModelProvider.Factory {
        private final ApiService apiService;

        public Factory(ApiService apiService) {
            this.apiService = apiService;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(MainActivityViewModel.class)) {
                return (T) new MainActivityViewModel(apiService);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}