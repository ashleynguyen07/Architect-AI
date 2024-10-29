package com.example.architectai.service;

import com.example.architectai.dto.SignInDto;
import com.example.architectai.dto.SignUpDto;
import com.example.architectai.dto.UserTransactionRequestDto;
import com.example.architectai.dto.UserUsageRequestDto;
import com.example.architectai.entity.ApplicationUser;
import com.example.architectai.entity.UserTransaction;
import com.example.architectai.entity.UserUsage;
import com.example.architectai.repository.ApplicationUserRepository;
import com.example.architectai.repository.UserTransactionRepository;
import com.example.architectai.repository.UserUsageRepository;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ApplicationUserRepository applicationUserRepository;
    private final UserUsageRepository userUsageRepository;
    private final UserTransactionRepository userTransactionRepository;
    private final ObjectMapper objectMapper;

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
    public UserUsage createUserUsage(UUID userId, UserUsageRequestDto userUsageRequestDto) throws JsonMappingException {
        UserUsage userUsage = new UserUsage();
        objectMapper.updateValue(userUsageRequestDto, userUsage);
        ApplicationUser user = applicationUserRepository.findById(userId).orElseThrow(() -> new IllegalStateException("User not found"));
        userUsage.setUser(user);
        return userUsageRepository.save(userUsage);
    }

    @Override
    public UserUsage updateUserUsage(UUID userId, UUID userUsageId, UserUsageRequestDto userUsageRequestDto) throws JsonMappingException {
        UserUsage userUsage = userUsageRepository.findById(userUsageId).orElseThrow(() -> new IllegalStateException("User usage not found"));
        objectMapper.updateValue(userUsageRequestDto, userUsage);
        return userUsageRepository.save(userUsage);
    }

    @Override
    public void deleteUserUsage(UUID userId, UUID userUsageId) {
        UserUsage userUsage = userUsageRepository.findById(userUsageId).orElseThrow(() -> new IllegalStateException("User usage not found"));
        userUsageRepository.delete(userUsage);
    }

    @Override
    public List<UserTransaction> getUserTransactions(UUID userId) {
        return userTransactionRepository.findByUser_Id(userId);
    }

    @Override
    public UserTransaction createUserTransaction(UUID userId, UserTransactionRequestDto userTransactionRequestDto) throws JsonMappingException {
        UserTransaction userTransaction = new UserTransaction();
        objectMapper.updateValue(userTransactionRequestDto, userTransaction);
        ApplicationUser user = applicationUserRepository.findById(userId).orElseThrow(() -> new IllegalStateException("User not found"));
        userTransaction.setUser(user);
        return userTransactionRepository.save(userTransaction);
    }

    @Override
    public UserTransaction updateUserTransaction(UUID userId, UUID userTransactionId, UserTransactionRequestDto userTransactionRequestDto) throws JsonMappingException {
        UserTransaction userTransaction = userTransactionRepository.findById(userTransactionId).orElseThrow(() -> new IllegalStateException("User transaction not found"));
        objectMapper.updateValue(userTransactionRequestDto, userTransaction);
        return userTransactionRepository.save(userTransaction);
    }

    @Override
    public void deleteUserTransaction(UUID userId, UUID userTransactionId) {
        UserTransaction userTransaction = userTransactionRepository.findById(userTransactionId).orElseThrow(() -> new IllegalStateException("User transaction not found"));
        userTransactionRepository.delete(userTransaction);
    }

}
