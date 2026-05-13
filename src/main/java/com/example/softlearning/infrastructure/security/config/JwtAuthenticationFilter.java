package com.example.softlearning.infrastructure.security.config;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.softlearning.infrastructure.persistence.jpa.auth.TokenRepository;
import com.example.softlearning.infrastructure.persistence.jpa.auth.UserEntity;
import com.example.softlearning.infrastructure.persistence.jpa.auth.UserRepository;
import com.example.softlearning.infrastructure.security.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtService jwtService,
                                   UserDetailsService userDetailsService,
                                   TokenRepository tokenRepository,
                                   UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (request.getServletPath().contains("/softlearning/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        final String username = jwtService.extractUsername(jwt);
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (username == null || authentication != null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            System.out.println("DEBUG JWT Filter - Username extracted: " + username);
            System.out.println("DEBUG JWT Filter - User authorities: " + userDetails.getAuthorities());
            
            var tokenOpt = tokenRepository.findByToken(jwt);
            System.out.println("DEBUG JWT Filter - Token found in DB: " + tokenOpt.isPresent());
            
            final boolean isTokenValid = tokenOpt
                    .map(token -> {
                        boolean expired = token.getIsExpired();
                        boolean revoked = token.getIsRevoked();
                        boolean valid = !expired && !revoked;
                        System.out.println("DEBUG JWT Filter - Token isExpired: " + expired + ", isRevoked: " + revoked + ", Valid: " + valid);
                        return valid;
                    })
                    .orElse(false);

            System.out.println("DEBUG JWT Filter - isTokenValid final: " + isTokenValid);

            if (isTokenValid) {
                final Optional<UserEntity> user = userRepository.findUserByUsername(username);
                if (user.isPresent() && jwtService.isTokenValid(jwt, user.get())) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("DEBUG JWT Filter - Authentication set successfully!");
                }
            } else {
                System.out.println("DEBUG JWT Filter - Token not valid - authentication NOT set");
            }
        } catch (Exception e) {
            System.out.println("DEBUG JWT Filter - EXCEPTION: " + e.getMessage());
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }
}
