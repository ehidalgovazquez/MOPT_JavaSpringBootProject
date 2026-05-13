package com.example.softlearning.presentation.api.rest.auth;

public record AuthRequest(
        String username,
        String password
) {
}
