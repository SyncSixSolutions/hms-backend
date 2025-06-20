package com.userManagement.userManagement.common;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.userManagement.userManagement.dto.LoginResponseDTO;
import lombok.Getter;

@Getter
public class SuccessLoginResponse implements LoginResponse {
    @JsonUnwrapped
    private final LoginResponseDTO loginResponseDto;

    public SuccessLoginResponse(LoginResponseDTO loginResponseDto) {
        this.loginResponseDto = loginResponseDto;
    }
}