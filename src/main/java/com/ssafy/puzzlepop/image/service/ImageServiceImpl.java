package com.ssafy.puzzlepop.image.service;

import com.ssafy.puzzlepop.image.domain.Image;
import com.ssafy.puzzlepop.image.domain.ImageCreateDto;
import com.ssafy.puzzlepop.image.domain.ImageDto;
import com.ssafy.puzzlepop.image.exception.ImageException;
import com.ssafy.puzzlepop.image.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    private ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    private static final String RELATIVE_PATH = "./uploads/image/";

    ///////////

    @Override
    public int createImage(MultipartFile file, ImageCreateDto imageCreateDto) throws ImageException {

        Path absolutePath = Paths.get(RELATIVE_PATH).toAbsolutePath();
        String absolutePathString = absolutePath.toString();

        String originalFilename = file.getOriginalFilename();
        String filenameExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        String filename = UUID.randomUUID().toString() + "_" + originalFilename;
        String filepath = Path.of(absolutePathString, filename).toString();

        if (originalFilename == null || filenameExtension == null || filename == null || filepath == null) {
            throw new ImageException("정상적인 이미지 파일이 아닙니다...");
        }

        // file의 확장자가 허용된 이미지 형식인지 확인 / 허용 확장자: png, jpeg, jpg, bmp
        if ("png".equals(filenameExtension) || "jpeg".equals(filenameExtension) || "jpg".equals(filenameExtension) || "bmp".equals(filenameExtension)) {
        } else { // not ok. return
            throw new ImageException("png, jpeg, jpg, bmp 형식의 파일만 업로드할 수 있습니다.");
        }

        // 파일 저장할 폴더 없다면 생성
        if (!absolutePath.toFile().exists()) {
            absolutePath.toFile().mkdirs();
        }

        // 파일 저장
        try {
            file.transferTo(new File(filepath));
        } catch (Exception e) {
            throw new ImageException("이미지 파일 업로드 중 에러 발생");
        }

        // image 객체에 file의 정보 뽑아서 담기
        Image image = new Image();

        image.setType(imageCreateDto.getType()); // 이미지타입
        image.setName(originalFilename); // 원본파일명
        image.setFilename(filename); // 서버저장파일명
        image.setFilepath(filepath); // 서버경로+서버저장파일명
        image.setFilenameExtension(filenameExtension); // 파일 확장자
        image.setUserId(imageCreateDto.getUserId()); // 업로드한 uid

        // 이미지 객체 db 저장
        try {
            imageRepository.save(image);
            return image.getId();
        } catch (Exception e) {
            throw new ImageException(e.getMessage());
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
            throw new ImageException("image matches to id doesn't exist");
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
