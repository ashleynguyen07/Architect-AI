package com.example.architectai.service;

import com.example.architectai.dto.SignInDto;
import com.example.architectai.dto.SignUpDto;
import com.example.architectai.entity.ApplicationUser;
import com.example.architectai.entity.UserTransaction;
import com.example.architectai.entity.UserUsage;
import com.example.architectai.repository.ApplicationUserRepository;
import com.example.architectai.repository.UserTransactionRepository;
import com.example.architectai.repository.UserUsageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ApplicationUserRepository applicationUserRepository;
    private final UserUsageRepository userUsageRepository;
    private final UserTransactionRepository userTransactionRepository;

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

    @Override
    public UserUsage getUserUsage(UUID userId) {
        return userUsageRepository.findByUser_Id(userId);
    }

    @Override
    public List<UserTransaction> getUserTransactions(UUID userId) {
        return userTransactionRepository.findByUser_Id(userId);
    }
}
