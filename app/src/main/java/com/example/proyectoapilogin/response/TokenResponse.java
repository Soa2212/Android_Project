package com.example.proyectoapilogin.response;

import com.example.proyectoapilogin.model.Token;

public class TokenResponse {
    private String process;
    private boolean message;

    public String getProcess() {
        return process;
    }

    public boolean getMessage() {
        return message;
    }

    public TokenResponse(String process, boolean message) {
        this.process = process;
        this.message = message;
    }
}