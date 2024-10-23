package com.example.architectai.repository;

import com.example.architectai.entity.RenderImageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RenderImageInfoRepository extends JpaRepository<RenderImageInfo, UUID> {

    RenderImageInfo findByOutputImage(String outputImage);
}
