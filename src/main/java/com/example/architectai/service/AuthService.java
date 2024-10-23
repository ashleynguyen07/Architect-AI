package com.example.architectai.service;

import com.example.architectai.dto.SignInDto;
import com.example.architectai.dto.SignUpDto;
import com.example.architectai.entity.ApplicationUser;
import com.example.architectai.entity.UserTransaction;
import com.example.architectai.entity.UserUsage;

import java.util.List;
import java.util.UUID;

public interface AuthService {

    ApplicationUser signUp(SignUpDto signUpDto);

    ApplicationUser signIn(SignInDto signInDto);

    List<ApplicationUser> getAllUsers();

    UserUsage getUserUsage(UUID userId);

    List<UserTransaction> getUserTransactions(UUID userId);
}
