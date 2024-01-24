package com.ssafy.puzzlepop.image.service;

import com.ssafy.puzzlepop.image.domain.ImageCreateDto;
import com.ssafy.puzzlepop.image.domain.ImageDto;
import com.ssafy.puzzlepop.image.exception.ImageException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    int createImage(MultipartFile file, ImageCreateDto imageCreateDto) throws ImageException;

    int updateImage(ImageDto imageDto) throws ImageException;

    void deleteImage(int id) throws ImageException;

    List<ImageDto> getAllImages() throws ImageException;

    List<ImageDto> getImagesByType(String type) throws ImageException;

    ImageDto getImageById(int id) throws ImageException;
}
