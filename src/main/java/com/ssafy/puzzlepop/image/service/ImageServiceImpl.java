package com.ssafy.puzzlepop.image.service;

import com.ssafy.puzzlepop.image.domain.Image;
import com.ssafy.puzzlepop.image.domain.ImageDto;
import com.ssafy.puzzlepop.image.exception.ImageException;
import com.ssafy.puzzlepop.image.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    private ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    ///////////

    @Override
    public int createImage(ImageDto imageDto) throws ImageException {
        Image image = new Image();

        image.setType(imageDto.getType());
        image.setName(imageDto.getName());
        image.setFilename(imageDto.getFilename());
        image.setFilepath(imageDto.getFilepath());
        image.setFilenameExtension(imageDto.getFilenameExtension());
        image.setUserId(imageDto.getUserId());
        image.setCreateTime(new Date());
        image.setUpdateTime(new Date());

        try {
            imageRepository.save(image);
            return image.getId();
        } catch (Exception e) {
            throw new ImageException("image create fail");
        }
    }

    @Override
    public int updateImage(ImageDto imageDto) throws ImageException {
        Image existImage = imageRepository.findById(imageDto.getId()).orElse(null);

        if (existImage != null) {
            existImage.setType(imageDto.getType());
            existImage.setName(imageDto.getName());
            existImage.setFilename(imageDto.getFilename());
            existImage.setFilepath(imageDto.getFilepath());
            existImage.setFilenameExtension(imageDto.getFilenameExtension());
            existImage.setUserId(imageDto.getUserId());
            existImage.setUpdateTime(new Date());

            imageRepository.save(existImage);
            return existImage.getId();
        } else {
            throw new ImageException("image to update doesn't exist");
        }
    }

    @Override
    public void deleteImage(int id) throws ImageException {

        // 존재하는 이미지인지 확인
        Image existImage = imageRepository.findById(id).orElse(null);

        if (existImage == null) { // 존재하지 않으면 exception throw
            throw new ImageException("image to delete doesn't exist");
        }

        // delete 시도
        try {
            imageRepository.deleteById(id);
        } catch (Exception e) {
            throw new ImageException("image delete failed");
        }
    }

    @Override
    public ImageDto getImageById(int id) throws ImageException {
        Image image;

        try {
            image = imageRepository.findById(id).orElse(null);
            if (image == null) { // 존재하는 id에 대한 요청만 허용한다고 가정. 필요 시 수정
                throw new ImageException("image matches to id doesn't exist");
            }

            return new ImageDto(image);
        } catch (Exception e) {
            throw new ImageException("failed to get image by id");
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

    @Override
    public List<ImageDto> getImagesByType(String type) throws ImageException {
        List<Image> imageList;

        try {
            imageList = imageRepository.findAllByType(type);
            return imageList.stream().map(ImageDto::new).collect(Collectors.toList());
        } catch (Exception e) {
            throw new ImageException("failed to get images by type");
        }
    }


}
