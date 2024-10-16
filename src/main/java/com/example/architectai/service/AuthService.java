package com.example.architectai.service;

import com.example.architectai.dto.SignInDto;
import com.example.architectai.dto.SignUpDto;
import com.example.architectai.entity.ApplicationUser;

import java.util.List;

public interface AuthService {

    ApplicationUser signUp(SignUpDto signUpDto);

    ApplicationUser signIn(SignInDto signInDto);

    List<ApplicationUser> getAllUsers();
}
