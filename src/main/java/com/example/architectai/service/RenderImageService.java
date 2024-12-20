package com.example.architectai.service;

import com.example.architectai.dto.RenderImageRequestDto;
import com.example.architectai.entity.RenderImageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface RenderImageService {

    RenderImageInfo renderImage(RenderImageRequestDto requestDto, MultipartFile image) throws IOException;

    byte[] exportImage(String name) throws IOException;
}
