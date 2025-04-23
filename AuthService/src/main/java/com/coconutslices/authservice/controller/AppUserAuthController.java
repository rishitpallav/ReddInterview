package com.coconutslices.authservice.controller;

import com.coconutslices.authservice.dto.AppUserRegisterRequest;
import com.coconutslices.authservice.dto.LoginRequest;
import com.coconutslices.authservice.dto.LoginResponse;
import com.coconutslices.authservice.service.AppUserService;
import com.coconutslices.authservice.service.TokenService;
import com.coconutslices.authservice.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AppUserAuthController {

    private final AppUserService appUserService;
    private final TokenService tokenService;

    private static final String PREFIX = "jwt:";

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody AppUserRegisterRequest appUserRegisterRequest) {
        appUserService.register(appUserRegisterRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = appUserService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().build();
        }

        String token = authHeader.substring(7);

        if (!tokenService.isTokenInCache(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        tokenService.deleteKey(token);
        return ResponseEntity.noContent().build();
    }
}
