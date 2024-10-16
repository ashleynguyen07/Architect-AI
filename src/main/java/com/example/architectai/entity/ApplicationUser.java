package com.example.architectai.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "application_users")
@Builder
@RequiredArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "tel_no", nullable = false)
    private String telNo;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "creation_time", nullable = false)
    private LocalDateTime creationTime;
}
