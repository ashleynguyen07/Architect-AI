package com.example.architectai.repository;

import com.example.architectai.entity.UserUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserUsageRepository extends JpaRepository<UserUsage, UUID> {


    UserUsage findByUser_Id(UUID id);
}
