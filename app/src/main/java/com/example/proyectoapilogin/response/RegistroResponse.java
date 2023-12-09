package com.example.proyectoapilogin.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegistroResponse {
    private String process;
    private ErrorMessage menssage;

    public RegistroResponse(String process, ErrorMessage menssage) {
        this.process = process;
        this.menssage = menssage;
    }

    public String getProcess() {
        return process;
    }

    public ErrorMessage getMenssage() {
        return menssage;
    }

    public static class ErrorMessage {
        @SerializedName("name")
        private List<String> name;
        @SerializedName("email")
        private List<String> email;
        @SerializedName("password")
        private List<String> password;
        @SerializedName("password_confirmation")
        private List<String> passwordConfirmation;

        public List<String> getName() {
            return name;
        }

        public List<String> getEmail() {
            return email;
        }

        public List<String> getPassword() {
            return password;
        }

        public List<String> getPasswordConfirmation() {
            return passwordConfirmation;
        }
    }
}