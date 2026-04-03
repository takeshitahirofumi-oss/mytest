package com.example.ecsite.config;

import com.example.ecsite.entity.User;
import com.example.ecsite.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {
    @Bean
    public CommandLineRunner loadData(UserRepository userRepository) {
        return args -> {
            if (userRepository.count() == 0) {
                PasswordEncoder encoder = new BCryptPasswordEncoder();
                User user = new User();
                user.setUserId("testuser");
                user.setPassword("{bcrypt}" + encoder.encode("testpass"));
                userRepository.save(user);
            }
        };
    }
}
