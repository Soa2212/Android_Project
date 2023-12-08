package com.example.proyectoapilogin.model;

public class Token {
    private String process;
    private boolean message;

    public String getProcess() {
        return process;
    }

    public boolean getMessage() {
        return message;
    }

    public Token(String process, boolean message) {
        this.process = process;
        this.message = message;
    }
}
