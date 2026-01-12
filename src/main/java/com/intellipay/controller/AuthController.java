package com.intellipay.controller;

import com.intellipay.dto.LoginRequestDto;
import com.intellipay.dto.LoginResponseDto;
import com.intellipay.dto.SignupRequestDto;
import com.intellipay.dto.SignupResponseDto;
import com.intellipay.security.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto)
    {
        System.out.println("LOGIN HIT");
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@Valid @RequestBody SignupRequestDto signupRequestDto)
    {
        System.out.println("SIGNUP HIT");
        return ResponseEntity.ok(authService.signup(signupRequestDto));
    }
}
