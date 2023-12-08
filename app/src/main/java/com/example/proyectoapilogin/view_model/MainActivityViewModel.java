package com.example.proyectoapilogin.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proyectoapilogin.model.TokenResponse;
import com.example.proyectoapilogin.retrofit.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends ViewModel {

    private final MutableLiveData<Boolean> tokenValid = new MutableLiveData<>();
    private final ApiService apiService;

    public MainActivityViewModel(ApiService apiService) {
        this.apiService = apiService;
    }

    public MutableLiveData<Boolean> getTokenValid() {
        return tokenValid;
    }

    public void verificarToken(String token) {
        Call<TokenResponse> call = apiService.verificarToken();

        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    TokenResponse apiResponse = response.body();
                    if (apiResponse.getProcess().equals("successful") && apiResponse.getMessage()) {
                        tokenValid.setValue(true);
                    } else {
                        tokenValid.setValue(false);
                    }
                } else {
                    tokenValid.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                tokenValid.setValue(false);
            }


        });
    }
}
