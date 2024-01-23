package com.ssafy.puzzlepop.image.repository;

import com.ssafy.puzzlepop.image.domain.Image;
import com.ssafy.puzzlepop.image.domain.ImageDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {

    List<Image> findAllByType(String type);

}
