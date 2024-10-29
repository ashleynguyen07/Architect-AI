package com.example.architectai.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserUsageRequestDto {

    private Boolean subscriber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer noGeneratingImages;
    private Integer noExportingImages;
}
