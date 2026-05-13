package com.example.softlearning.presentation.api.rest.auth;

public record RegisterRequest(
        String username,
        String password
) {
}
