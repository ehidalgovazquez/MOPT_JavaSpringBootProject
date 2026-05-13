package com.example.softlearning.infrastructure.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.softlearning.infrastructure.persistence.jpa.auth.TokenRepository;
import com.example.softlearning.infrastructure.security.auth.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final TokenRepository tokenRepository;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter,
                          TokenRepository tokenRepository) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.tokenRepository = tokenRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationProvider authenticationProvider) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/softlearning/auth/**").permitAll()
                        
                        // Books endpoints
                        .requestMatchers(HttpMethod.GET, "/softlearning/books/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/softlearning/books/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/softlearning/books/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/softlearning/books/**").hasAnyRole("ADMIN")

                        // Clients endpoints
                        .requestMatchers(HttpMethod.GET, "/softlearning/clients").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.GET, "/softlearning/clients/**").hasAnyRole("ADMIN", "MANAGER", "CLIENT")
                        .requestMatchers(HttpMethod.POST, "/softlearning/clients/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/softlearning/clients/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/softlearning/clients/**").hasRole("ADMIN")

                        // Orders endpoints
                        .requestMatchers(HttpMethod.GET, "/softlearning/orders").hasAnyRole("ADMIN", "MANAGER", "CLIENT")
                        .requestMatchers(HttpMethod.GET, "/softlearning/orders/**").hasAnyRole("ADMIN", "MANAGER", "CLIENT")
                        .requestMatchers(HttpMethod.POST, "/softlearning/orders/**").hasAnyRole("ADMIN", "MANAGER", "CLIENT")
                        .requestMatchers(HttpMethod.PUT, "/softlearning/orders/**").hasAnyRole("ADMIN", "MANAGER", "CLIENT")
                        .requestMatchers(HttpMethod.DELETE, "/softlearning/orders/**").hasAnyRole("ADMIN", "MANAGER", "CLIENT")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(403);
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.getWriter().write("Acceso denegado: No tienes permisos para realizar esta acción");
                        })
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(401);
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.getWriter().write("No autenticado: Debes enviar un token válido");
                        })
                )
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .addLogoutHandler(this::logout)
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(jakarta.servlet.http.HttpServletResponse.SC_OK);
                        })
                )
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private void logout(jakarta.servlet.http.HttpServletRequest request,
                        jakarta.servlet.http.HttpServletResponse response,
                        org.springframework.security.core.Authentication authentication) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        final String jwt = authHeader.substring(7);
        tokenRepository.findByToken(jwt).ifPresent(token -> {
            token.setIsExpired(true);
            token.setIsRevoked(true);
            tokenRepository.save(token);
            org.springframework.security.core.context.SecurityContextHolder.clearContext();
        });
    }
}
