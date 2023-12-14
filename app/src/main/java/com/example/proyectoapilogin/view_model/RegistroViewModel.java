package com.example.proyectoapilogin.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyectoapilogin.Repositories.RegistroRepository;
import com.example.proyectoapilogin.retrofit.ApiService;

public class RegistroViewModel extends ViewModel {
    private final RegistroRepository registroRepository;

    public RegistroViewModel(RegistroRepository registroRepository) {
        this.registroRepository = registroRepository;
    }

    public LiveData<String> getRegisterProcess() {
        return registroRepository.getRegisterProcess();
    }

    public LiveData<String> getRegisterError() {
        return registroRepository.getRegisterError();
    }

    public LiveData<Boolean> getSuccessfulRegister() {
        return registroRepository.getSuccessfulRegister();
    }

    public void verifyRegister(String nombre, String email, String password, String passwordconfirm) {
        registroRepository.verifyRegister(nombre, email, password, passwordconfirm);
    }

    public static class Factory implements ViewModelProvider.Factory {
        private final RegistroRepository registroRepository;

        public Factory(ApiService apiService) {
            this.registroRepository = new RegistroRepository(apiService);
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            if (modelClass.isAssignableFrom(RegistroViewModel.class)) {
                return (T) new RegistroViewModel(registroRepository);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
