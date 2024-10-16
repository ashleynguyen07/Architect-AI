package com.example.architectai.service;

import com.example.architectai.dto.SignInDto;
import com.example.architectai.dto.SignUpDto;
import com.example.architectai.entity.ApplicationUser;
import com.example.architectai.repository.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ApplicationUserRepository applicationUserRepository;

    @Override
    public ApplicationUser signUp(SignUpDto signUpDto) {
        boolean existsByEmail = applicationUserRepository.existsByEmail(signUpDto.getEmail());

        if (existsByEmail) {
            throw new IllegalStateException("Email already taken");
        }
        ApplicationUser savedUser = ApplicationUser.builder()
                .firstName(signUpDto.getFirstName().trim())
                .lastName(signUpDto.getLastName().trim())
                .email(signUpDto.getEmail().trim())
                .telNo(signUpDto.getTelNo())
                .creationTime(LocalDateTime.now())
                .isActive(true)
                .password(signUpDto.getPassword())
                .build();
        return applicationUserRepository.save(savedUser);
    }

    @Override
    public ApplicationUser signIn(SignInDto signInDto) {
        boolean existsByEmail = applicationUserRepository.existsByEmail(signInDto.getEmail());

        if (!existsByEmail) {
            throw new IllegalStateException("Email not found");
        }
        ApplicationUser currentUser = applicationUserRepository.findByEmail(signInDto.getEmail());

        if (currentUser.getPassword().equals(signInDto.getPassword())) {
            return currentUser;
        } else {
            throw new IllegalStateException("Wrong password");
        }
    }

    @Override
    public List<ApplicationUser> getAllUsers() {
        return applicationUserRepository.findAll();
    }
}
