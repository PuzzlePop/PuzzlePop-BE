package com.ssafy.puzzlepop.image.service;

import com.ssafy.puzzlepop.image.domain.Image;
import com.ssafy.puzzlepop.image.domain.ImageDto;
import com.ssafy.puzzlepop.image.exception.ImageException;
import com.ssafy.puzzlepop.image.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    private ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public int createImage(ImageDto imageDto) throws ImageException {
        Image image = new Image(imageDto.getId(), imageDto.getCode(), imageDto.getName(), imageDto.getFilename(), imageDto.getFilepath(), imageDto.getFilesize(), imageDto.getUserId());

        try {
            imageRepository.save(image);
        } catch(Exception e) {
            throw new ImageException("image create fail");
        }

        return image.getId();
    }

    // TODO: JPA에서의 update 공부해서 update 기능 완성
    @Override
    public int updateImage(ImageDto imageDto) throws ImageException {
//        imageRepository.
//        if (imageRepository.updateImage(imageDto) == 1) {
//            return imageDto.getId();
//        }
//
//        throw new ImageException("image update fail");

        return 0;
    }

    @Override
    public boolean deleteImage(int id) throws ImageException {

        try {
            imageRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new ImageException("image delete failed");
        }
    }

    @Override
    public List<ImageDto> getAllImages() throws ImageException {
        List<Image> imageList;

        try {
            imageList = imageRepository.findAll();
            return imageList.stream().map(ImageDto::new).collect(Collectors.toList());
        } catch (Exception e) {
            throw new ImageException("failed to get all images");
        }
    }

}
