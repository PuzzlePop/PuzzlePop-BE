package com.ssafy.puzzlepop.image.service;

import com.ssafy.puzzlepop.image.domain.ImageDataResponseDto;
import com.ssafy.puzzlepop.image.domain.ImageRequestDto;
import com.ssafy.puzzlepop.image.domain.ImageDto;
import com.ssafy.puzzlepop.image.domain.ImageResponseDto;
import com.ssafy.puzzlepop.image.exception.ImageException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    Long createImage(MultipartFile file, ImageRequestDto imageRequestDto) throws ImageException;

    Long updateImage(ImageDto imageDto) throws ImageException;

    void deleteImage(Long id) throws ImageException;

    String getBase64ImageById(Long id) throws ImageException;

    ImageResponseDto getImageInfoById(Long id) throws ImageException;

    List<ImageResponseDto> getAllImages() throws ImageException;

    List<ImageResponseDto> getImagesByType(String type, Long userId) throws ImageException;

    List<ImageResponseDto> getImagesByUserId(Long userId) throws ImageException;

    ImageDto getImageDtoById(Long id) throws ImageException;

    List<ImageDataResponseDto> getAllPuzzleImages() throws ImageException;
}
