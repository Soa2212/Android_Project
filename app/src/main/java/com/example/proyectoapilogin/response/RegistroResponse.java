package com.example.proyectoapilogin.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

public class RegistroResponse {
    private String process;
    private JsonElement message;

    public RegistroResponse(String process, JsonElement message) {
        this.process = process;
        this.message = message;
    }

    public JsonElement getMessage() {
        return message;
    }

    public String getProcess() {
        return process;
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