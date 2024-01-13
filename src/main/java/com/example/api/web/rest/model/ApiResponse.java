package com.example.api.web.rest.model;

public class ApiResponse {
    private String message;
    private Integer code;

    public ApiResponse(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
