package com.intellipay.service;

import com.intellipay.dto.UserDto;
import com.intellipay.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ModelMapper modelMapper;

    public UserDto getCurrentUserProfile() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User is not authenticated");
        }

        User user = (User) authentication.getPrincipal();

        return modelMapper.map(user, UserDto.class);
    }
}
