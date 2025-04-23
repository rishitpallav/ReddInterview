package com.coconutslices.authservice.service;

import com.coconutslices.authservice.dto.AppUserRegisterRequest;
import com.coconutslices.authservice.dto.LoginRequest;
import com.coconutslices.authservice.dto.LoginResponse;
import com.coconutslices.authservice.model.AppUser;
import com.coconutslices.authservice.model.Role;
import com.coconutslices.authservice.repository.AppUserRepository;
import com.coconutslices.authservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppUserService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public void register(AppUserRegisterRequest appUserRegisterRequest) {
        AppUser appUser = AppUser.builder()
                .firstName(appUserRegisterRequest.getFirstName())
                .lastName(appUserRegisterRequest.getLastName())
                .email(appUserRegisterRequest.getEmail().trim())
                .password(passwordEncoder.encode(appUserRegisterRequest.getPassword()))
                .role(Role.USER)
                .build();

        try {
            appUserRepository.save(appUser);
        } catch (DataIntegrityViolationException e) {
            log.error("❌ Error saving entity: Data Integrity Violation occurred: " + e.getMessage());
        } catch (DataAccessException e) {
            log.error("❌ Error saving entity: Error accessing the database: " + e.getMessage());
        } catch (Exception e) {
            log.error("❌ An unexpected error has occurred: " + e.getMessage());
        }
        log.info("✅ New app user registered: {}" + appUser.getEmail());
    }

    public LoginResponse login(LoginRequest request) {

        Authentication authToken = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        );

        authenticationManager.authenticate(authToken);

        AppUser user = appUserRepository.findByEmail(request.getEmail());
        if (user == null) {
            throw new RuntimeException("❌ User not found");
        }

        String token = jwtUtil.generateToken(user);

        long expirationMillis = jwtUtil.getJwtExpirationMs();
        tokenService.storeToken(token, expirationMillis);

        return new LoginResponse(token);
    }
}
