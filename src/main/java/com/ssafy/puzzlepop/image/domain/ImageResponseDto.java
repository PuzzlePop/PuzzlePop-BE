package com.ssafy.puzzlepop.image.domain;

import lombok.*;
import org.springframework.core.io.UrlResource;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ImageResponseDto {

    // 이미지 조회 요청 시 사용

    private int id;
    private String filename;
    private UrlResource imageUrl;

}
