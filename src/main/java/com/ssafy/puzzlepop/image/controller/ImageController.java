package com.ssafy.puzzlepop.image.controller;

import com.ssafy.puzzlepop.image.domain.ImageDataResponseDto;
import com.ssafy.puzzlepop.image.domain.ImageRequestDto;
import com.ssafy.puzzlepop.image.domain.ImageDto;
import com.ssafy.puzzlepop.image.domain.ImageResponseDto;
import com.ssafy.puzzlepop.image.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.util.List;

// TODO: exception catch 시 에러 메세지 그대로 보내는 리턴하는 부분 리팩토링 필요
// TODO: 작업하려는 이미지의 userId와 accessToken 발급받은 userId가 일치하는지 확인 필요
// TODO: 이미지에 대한 update 작업이 뭔지 정의 필요 & 정의에 맞춰 로직 수정 필요

@RestController
@RequestMapping("/image")
public class ImageController {

    private static final Long ADMIN_USER_ID = (long) 0;
    private final ImageService imageService;

    @Autowired
    private ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    public ResponseEntity<?> createImage(@RequestParam MultipartFile file, @RequestParam String type) {
        // TODO: ADMIN_USER_ID 대신 요청 보낸 유저의 ID로 교체할 것
        ImageRequestDto imageRequestDto = new ImageRequestDto(type, ADMIN_USER_ID);

        try {
            Long id = imageService.createImage(file, imageRequestDto);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // 보류
    //@PutMapping
    public ResponseEntity<?> updateImage(@RequestBody ImageDto imageDto) {

        try {
            Long id = imageService.updateImage(imageDto);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable Long id) {

        try {
            imageService.deleteImage(id);
            return ResponseEntity.status(HttpStatus.OK).body("DELETE OK: "+id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<?> findImageInfoById(@PathVariable Long id) {
        try {
            ImageResponseDto imageResponseDto = imageService.getImageInfoById(id);
            return ResponseEntity.status(HttpStatus.OK).body(imageResponseDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findImageById(@PathVariable Long id) {
        try {
            String base64Image = imageService.getBase64ImageById(id);
            return ResponseEntity.status(HttpStatus.OK).body(base64Image);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> findAllImages() {

        try {
            List<ImageResponseDto> imageList = imageService.getAllImages();
            return ResponseEntity.status(HttpStatus.OK).body(imageList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/list/puzzle")
    public ResponseEntity<?> findAllPuzzleImages() {
        try {
            List<ImageDataResponseDto> imageDataList = imageService.getAllPuzzleImages();
            return ResponseEntity.status(HttpStatus.OK).body(imageDataList);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("FAIL");
        }
    }

    @GetMapping("/list/info/{type}")
    public ResponseEntity<?> findImagesByType(@PathVariable String type) {
        // TODO: 요청 보낸 사용자의 userID로 대체해야 함
        Long userId = ADMIN_USER_ID;
        try {
            List<ImageResponseDto> imageList = imageService.getImagesByType(type, userId);
            return ResponseEntity.status(HttpStatus.OK).body(imageList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @PostMapping("/list")
    public ResponseEntity<?> findImagesByUserId(@RequestBody ImageResponseDto imageResponseDto) {
        try {
            List<ImageResponseDto> imageList = imageService.getImagesByUserId(imageResponseDto.getUserId());
            return ResponseEntity.status(HttpStatus.OK).body(imageList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
