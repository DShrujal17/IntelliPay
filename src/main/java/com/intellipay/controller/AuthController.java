package com.intellipay.controller;

import com.intellipay.dto.*;
import com.intellipay.security.AuthService;
import com.intellipay.security.AuthUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthUtil authUtil;

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

    @PutMapping("/update")
    public ResponseEntity<UserDto>updateUser(@RequestHeader("Authorization") String authHeader , @Valid @RequestBody UpdateUserRequestDto updateUserRequestDto)
    {
        System.out.println("UPDATE HIT");

        String token = authHeader.replace("Bearer ", "");
        UUID id = authUtil.extractUserId(token);
        return ResponseEntity.ok(authService.updateUser(id , updateUserRequestDto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestHeader("Authorization") String authHeader)
    {
        String token = authHeader.substring(7);
        UUID id = authUtil.extractUserId(token);

        return ResponseEntity.ok(authService.deleteUser(id));
    }
}
