package com.intellipay.Config;

import com.intellipay.entity.Role;
import com.intellipay.entity.User;
import com.intellipay.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Configuration
@RequiredArgsConstructor
public class AdminInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner createAdminAtStartup()
    {
        return args -> {

            String adminEmail = "admin@intellipay.com";
            if (userRepository.existsByEmail(adminEmail)) {
                return; // ✅ admin already exists
            }

            User admin = User.builder()
                    .username("admin")
                    .email(adminEmail)
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ADMIN)
                    .build();

            userRepository.save(admin);

            System.out.println("✅ DEFAULT ADMIN CREATED");
        };
    }
}
