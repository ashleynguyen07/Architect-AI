package com.example.architectai.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
public class RenderImageRequestDto {

    @NonNull
    private String style;
    @NonNull
    private String material;
    @NonNull
    private Integer width;
    @NonNull
    private Integer height;
    private Integer numInferenceSteps;
    private Number guidanceScale;
}
