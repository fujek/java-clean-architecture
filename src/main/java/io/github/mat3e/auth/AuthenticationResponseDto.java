package io.github.mat3e.auth;

public class AuthenticationResponseDto {
    private final String token;

    AuthenticationResponseDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
