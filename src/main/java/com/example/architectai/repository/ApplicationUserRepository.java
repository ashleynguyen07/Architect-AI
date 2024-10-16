package com.example.architectai.repository;

import com.example.architectai.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, UUID> {

    boolean existsByEmail(String email);

    ApplicationUser findByEmail(String email);
}
