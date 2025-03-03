package com.tks.chatapp.response.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public class JwtResponse {
    private String accessToken;
    private String refreshToken;
}
