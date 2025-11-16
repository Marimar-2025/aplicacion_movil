package com.app.backend.dto;

public class MessagerResponse {
    private String message;

    public MessagerResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}