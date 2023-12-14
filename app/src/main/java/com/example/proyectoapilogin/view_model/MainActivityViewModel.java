package com.example.proyectoapilogin.view_model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyectoapilogin.model.Token;
import com.example.proyectoapilogin.response.LoginResponse;
import com.example.proyectoapilogin.response.TokenResponse;
import com.example.proyectoapilogin.retrofit.ApiService;
import com.example.proyectoapilogin.views.Login;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends ViewModel {
    private MutableLiveData<Boolean> isTokenValid = new MutableLiveData<>();
    private MutableLiveData<Boolean> logoutValid = new MutableLiveData<>();
    private ApiService apiService;

    public MainActivityViewModel(ApiService apiService) {
        this.apiService = apiService;
    }

    public LiveData<Boolean> getTokenValidity() {
        return isTokenValid;
    }
    public LiveData<Boolean> getLogoutValidity() {
        return logoutValid;
    }

    public void logout(Context context) {
        Log.e("DetalleHabitacionViewModel", "Entro a eliminarHabitacion");
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        apiService.logout("Bearer " + token).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                Log.e("DetalleHabitacionViewModel", response.body().getProcess());
                if (response.body().getProcess().equals("successful")) {
                    logoutValid.setValue(true);
                }
                else {
                    logoutValid.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                Log.e("DetalleHabitacionViewModel", "Error en la petición");
                logoutValid.setValue(false);
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