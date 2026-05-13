package com.example.softlearning.infrastructure.security.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.softlearning.infrastructure.persistence.jpa.JpaClientRepository;
import com.example.softlearning.infrastructure.persistence.jpa.auth.RoleEntity;
import com.example.softlearning.infrastructure.persistence.jpa.auth.RoleRepository;
import com.example.softlearning.infrastructure.persistence.jpa.auth.TokenEntity;
import com.example.softlearning.infrastructure.persistence.jpa.auth.TokenRepository;
import com.example.softlearning.infrastructure.persistence.jpa.auth.UserEntity;
import com.example.softlearning.infrastructure.persistence.jpa.auth.UserRepository;
import com.example.softlearning.infrastructure.security.config.RoleEnum;
import com.example.softlearning.presentation.api.rest.auth.AuthRequest;
import com.example.softlearning.presentation.api.rest.auth.RegisterRequest;
import com.example.softlearning.presentation.api.rest.auth.TokenResponse;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JpaClientRepository jpaClientRepository;

    public TokenResponse register(RegisterRequest request) {
        if (userRepository.findUserByUsername(request.username()).isPresent()) {
            throw new IllegalArgumentException("El usuario ya existe");
        }

        RoleEntity role = roleRepository.findByRoleEnum(RoleEnum.CLIENT)
                .orElseGet(() -> roleRepository.save(new RoleEntity(RoleEnum.CLIENT)));

        UserEntity user = new UserEntity(request.username(), passwordEncoder.encode(request.password()), true, true, true, true, Set.of(role));
        UserEntity savedUser = userRepository.save(user);

        Integer clientId = resolveClientIdForUser(savedUser);
        String jwtToken = jwtService.generateToken(savedUser, clientId);
        String refreshToken = jwtService.generateRefreshToken(savedUser, clientId);
        saveUserToken(savedUser, jwtToken);
        return new TokenResponse(jwtToken, refreshToken);
    }

    public TokenResponse authenticate(final AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        final UserEntity user = userRepository.findUserByUsername(request.username()).orElseThrow();
        final Integer clientId = resolveClientIdForUser(user);
        final String accessToken = jwtService.generateToken(user, clientId);
        final String refreshToken = jwtService.generateRefreshToken(user, clientId);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        return new TokenResponse(accessToken, refreshToken);
    }

    public TokenResponse refreshToken(final String authentication) {
        if (authentication == null || !authentication.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Encabezado Authorization inválido");
        }
        final String refreshToken = authentication.substring(7);
        final String userEmail = jwtService.extractUsername(refreshToken);
        final UserEntity user = userRepository.findUserByUsername(userEmail).orElseThrow();
        if (!jwtService.isTokenValid(refreshToken, user)) {
            throw new IllegalArgumentException("Refresh token inválido");
        }

        final Integer clientId = resolveClientIdForUser(user);
        final String accessToken = jwtService.generateToken(user, clientId);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        return new TokenResponse(accessToken, refreshToken);
    }

    private void saveUserToken(UserEntity user, String jwtToken) {
        System.out.println("DEBUG saveUserToken - Creating token with isExpired=false, isRevoked=false");
        TokenEntity token = new TokenEntity(jwtToken, "BEARER", false, false, user);
        System.out.println("DEBUG saveUserToken - Before save: isExpired=" + token.getIsExpired() + ", isRevoked=" + token.getIsRevoked());
        tokenRepository.save(token);
        System.out.println("DEBUG saveUserToken - After save: isExpired=" + token.getIsExpired() + ", isRevoked=" + token.getIsRevoked());
        
        // Verify what's in DB immediately after save
        var savedToken = tokenRepository.findByToken(jwtToken);
        if (savedToken.isPresent()) {
            System.out.println("DEBUG saveUserToken - Verified in DB: isExpired=" + savedToken.get().getIsExpired() + ", isRevoked=" + savedToken.get().getIsRevoked());
        } else {
            System.out.println("DEBUG saveUserToken - Token NOT found in DB after save!");
        }
    }

    private Integer resolveClientIdForUser(UserEntity user) {
        if (user.getRoles().stream().anyMatch(role -> role.getRoleEnum() == RoleEnum.CLIENT)) {
            return user.getId().intValue();
        }
        return null;
    }

    private void revokeAllUserTokens(final UserEntity user) {
        final List<TokenEntity> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (!validUserTokens.isEmpty()) {
            validUserTokens.forEach(token -> {
                token.setIsExpired(true);
                token.setIsRevoked(true);
            });
            tokenRepository.saveAll(validUserTokens);
        }
    }
}
