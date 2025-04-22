package com.coconutslices.authservice.controller;

import com.coconutslices.authservice.dto.AppUserRegisterRequest;
import com.coconutslices.authservice.service.AppUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AppUserAuthController {

    private final AppUserService appUserService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody AppUserRegisterRequest appUserRegisterRequest) {
        appUserService.register(appUserRegisterRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
