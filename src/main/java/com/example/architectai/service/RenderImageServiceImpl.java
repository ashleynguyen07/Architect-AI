package com.example.architectai.service;

import com.example.architectai.dto.RenderImageRequestDto;
import com.example.architectai.dto.RenderImageResponseDto;
import com.example.architectai.entity.RenderImageInfo;
import com.example.architectai.repository.RenderImageInfoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.Objects;
import java.util.zip.Inflater;

@Service
@RequiredArgsConstructor
public class RenderImageServiceImpl implements RenderImageService {

    private final RenderImageInfoRepository renderImageInfoRepository;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${ai_server_url}")
    private String aiServerUrl;

    @Value("${store.images.directory}")
    private String imageDirectory;

    @Override
    public RenderImageInfo renderImage(RenderImageRequestDto requestDto, MultipartFile image) throws IOException {
        ByteArrayResource byteArrayResource = new ByteArrayResource(image.getBytes()) {
            @Override
            public String getFilename() {
                return image.getOriginalFilename(); // Return the original filename
            }
        };

        // Create a MultiValueMap to hold the request parts
        MultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<>();
        requestMap.add("image", byteArrayResource); // Add the image file
        requestMap.add("style", requestDto.getStyle());
        requestMap.add("material", requestDto.getMaterial());
        requestMap.add("width", requestDto.getWidth());
        requestMap.add("height", requestDto.getHeight());
        requestMap.add("numInferenceSteps", requestDto.getNumInferenceSteps());
        requestMap.add("guidanceScale", requestDto.getGuidanceScale());

        // Prepare the request entity
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(requestMap);

        // Send the POST request
        ResponseEntity<String> response = restTemplate.exchange(
                aiServerUrl,
                HttpMethod.POST,
                requestEntity,
                String.class // Change to String to capture raw response
        );

        RenderImageResponseDto renderImageResponseDto = objectMapper.readValue(response.getBody(), RenderImageResponseDto.class);

        if (renderImageResponseDto == null || renderImageResponseDto.getImage() == null) {
            throw new IllegalArgumentException("Invalid response: " + response.getBody());
        }

        Path path = Paths.get(imageDirectory);
        if (Files.notExists(path)) {
            Files.createDirectories(path);
        }

        // Decode the Base64 image
        byte[] decodedBytes = Base64.getDecoder().decode(renderImageResponseDto.getImage());

        // Create a new file name for the output image
        String outputFileName = path + "rendered_" + image.getOriginalFilename(); // You might want to customize this

        // Write the decoded bytes to the new file
        File outputFile = new File(path.toFile(), outputFileName);
        FileUtils.writeByteArrayToFile(outputFile, decodedBytes);

        RenderImageInfo renderImageInfo = RenderImageInfo.builder()
                .style(requestDto.getStyle())
                .width(requestDto.getWidth())
                .height(requestDto.getHeight())
                .outputImage(outputFileName)
                .imageData(decodedBytes)
                .build();

        renderImageInfoRepository.save(renderImageInfo);
        return renderImageInfo;
    }

    @Override
    public byte[] exportImage(String name) throws IOException {
        RenderImageInfo renderImageInfo = renderImageInfoRepository.findByOutputImage(name);
        return decompressImage(renderImageInfo.getImageData());
    }

    public static byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
        } catch (Exception exception) {
        }
        return outputStream.toByteArray();
    }
}
