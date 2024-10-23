package com.example.architectai.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "render_image_info")
@Builder
@RequiredArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class RenderImageInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "mode")
    private String mode;

    @Column(name = "type")
    private String type;

    @Column(name = "style")
    private String style;

    @Column(name = "frame")
    private String frame;

    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;

    @Column(name = "input_image")
    private String inputImage;

    @Column(name = "image_number")
    private Integer image_number;

    @Column(name = "similarity_level")
    private String similarityLevel;

    @Column(name = "output_image")
    private String outputImage;

    @Column(name = "image_data")
    private byte[] imageData;
}
