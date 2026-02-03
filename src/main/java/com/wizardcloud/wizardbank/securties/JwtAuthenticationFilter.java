package com.wizardcloud.wizardbank.securties;

import com.wizardcloud.wizardbank.entities.UserEntity;
import com.wizardcloud.wizardbank.enums.UserStatus;
import com.wizardcloud.wizardbank.exceptions.ResourceNotFoundException;
import com.wizardcloud.wizardbank.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.wizardcloud.wizardbank.services.JwtService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;


    JwtAuthenticationFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        Claims payload = jwtService.validateToken(token);

        UserEntity user = userRepository.findById((UUID) payload.get("userId")).orElseThrow(() -> new ResourceNotFoundException("UNAUTHORIZED"));

        List<String> roles = (List<String>) payload.get("roles");

        List<SimpleGrantedAuthority> authorities = roles.stream()
            .map(SimpleGrantedAuthority::new)
            .toList(Collectors.toList());

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            user.getEmail(),
            null,
            payload.get("roles")
        );


        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
