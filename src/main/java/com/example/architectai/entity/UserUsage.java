package com.example.architectai.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_usage")
@Builder
@RequiredArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class UserUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private ApplicationUser user;

    @Column(name = "subscriber")
    private Boolean subscriber;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "no_generating_images")
    private Integer noGeneratingImages;

    @Column(name = "no_exporting_images")
    private Integer noExportingImages;
}
