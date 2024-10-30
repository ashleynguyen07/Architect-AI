package com.example.architectai.service;

import com.example.architectai.dto.SignInDto;
import com.example.architectai.dto.SignUpDto;
import com.example.architectai.dto.UserTransactionRequestDto;
import com.example.architectai.dto.UserUsageRequestDto;
import com.example.architectai.entity.ApplicationUser;
import com.example.architectai.entity.Storage;
import com.example.architectai.entity.UserTransaction;
import com.example.architectai.entity.UserUsage;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.util.List;
import java.util.UUID;

public interface AuthService {

    ApplicationUser signUp(SignUpDto signUpDto);

    ApplicationUser signIn(SignInDto signInDto);

    List<ApplicationUser> getAllUsers();

    UserUsage getUserUsage(UUID userId);

    UserUsage createUserUsage(UUID userId, UserUsageRequestDto userUsageRequestDto) throws JsonMappingException;

    UserUsage updateUserUsage(UUID userId, UUID userUsageId, UserUsageRequestDto userUsageRequestDto) throws JsonMappingException;

    void deleteUserUsage(UUID userId, UUID userUsageId);

    List<UserTransaction> getUserTransactions(UUID userId);

    UserTransaction createUserTransaction(UUID userId, UserTransactionRequestDto userTransactionRequestDto) throws JsonMappingException;

    UserTransaction updateUserTransaction(UUID userId, UUID userTransactionId, UserTransactionRequestDto userTransactionRequestDto) throws JsonMappingException;

    void deleteUserTransaction(UUID userId, UUID userTransactionId);

    String write(Storage storage);

    String update(Storage storage);

    byte[] read(Storage storage);

    List<String> listFiles(Storage storage);

    void delete(Storage storage);

    void createContainer();

    void deleteContainer();
}
