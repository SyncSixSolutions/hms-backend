package com.userManagement.userManagement.common;

import lombok.Getter;

@Getter
public class ErrorLoginResponse implements LoginResponse {
    private final String message;

    public ErrorLoginResponse(String message) {
        this.message = message;
    }
}