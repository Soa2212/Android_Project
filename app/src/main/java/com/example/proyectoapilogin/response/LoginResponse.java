package com.example.proyectoapilogin.response;

public class LoginResponse {
    private String process;
    private String message;
    private String token;

    public String getProcess() {
        return process;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {return token;}


    public LoginResponse(String process, String message, String token) {
        this.process = process;
        this.message = message;
        this.token = token;
    }
}
