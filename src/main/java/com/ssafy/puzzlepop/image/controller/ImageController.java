package com.ssafy.puzzlepop.image.controller;

import com.ssafy.puzzlepop.image.domain.ImageDto;
import com.ssafy.puzzlepop.image.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    public ResponseEntity<?> createImage(@RequestBody ImageDto imageDto) {

        try {
            int id = imageService.createImage(imageDto);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        } catch (/*ImageExeption*/Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> updateImage(@RequestBody ImageDto imageDto) {

        try {
            int id = imageService.updateImage(imageDto);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @DeleteMapping
    public ResponseEntity<?> deleteImage(@RequestBody int id) {

        try {
            imageService.deleteImage(id);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> findAllImages() {

        try {
            List<ImageDto> imageList = imageService.getAllImages();
            return ResponseEntity.status(HttpStatus.OK).body(imageList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
