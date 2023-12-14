package com.example.proyectoapilogin.view_model;

import android.content.Context;
import com.example.proyectoapilogin.Repositories.LoginRepository;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {
    private final MutableLiveData<String> loginError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> block = new MutableLiveData<>();
    private final MutableLiveData<Boolean> finishActivity = new MutableLiveData<>();
    private final MutableLiveData<Boolean> successfulLogin = new MutableLiveData<>();
    private final LoginRepository loginRepository;

    public LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
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
        MutableLiveData<Boolean> loginResult = loginRepository.verifyLogin(email, password);

        loginResult.observeForever(result -> {
            if (result) {
                loginError.setValue("Iniciando sesión...");
                block.setValue(true);
                successfulLogin.setValue(true);
            } else {
                block.setValue(false);
                loginError.setValue("Error al iniciar sesión. Verifique sus datos");
            }
        });
    }
}
