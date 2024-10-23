package com.example.architectai.repository;

import com.example.architectai.entity.UserTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserTransactionRepository extends JpaRepository<UserTransaction, UUID> {

    List<UserTransaction> findByUser_Id(UUID userId);
}
