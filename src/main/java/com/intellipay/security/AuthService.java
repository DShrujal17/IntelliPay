package com.intellipay.security;

import com.intellipay.dto.*;
import com.intellipay.entity.User;
import com.intellipay.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    public LoginResponseDto login(@Valid LoginRequestDto loginRequestDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getEmail(),
                        loginRequestDto.getPassword()
                )
        );

        User user = (User)authentication.getPrincipal();

        String token = authUtil.generateToken(user);

        UserDto userDto = modelMapper.map(user , UserDto.class);

        return LoginResponseDto.builder()
                .token(token)
                .user(userDto)
                .build();
    }


    public SignupResponseDto signup(@Valid SignupRequestDto signupRequestDto) {

        if("ADMIN".equalsIgnoreCase(signupRequestDto.getRole())) {
            throw new RuntimeException("ADMIN role cannot be created via signup");
        }

        if(userRepository.existsByUsername(signupRequestDto.getUsername())){
            throw new RuntimeException("Username already exists");
        }

        if(userRepository.existsByEmail(signupRequestDto.getUsername())){
            throw new RuntimeException("Email already exists");
        }

        User user = modelMapper.map(signupRequestDto , User.class);

        user.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));

        User savedUser = userRepository.save(user);

        UserDto userDto = modelMapper.map(savedUser , UserDto.class);

        return SignupResponseDto.builder()
                .message("User saved successfully")
                .user(userDto)
                .build();
    }
}
