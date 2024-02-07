package com.ssafy.puzzlepop.image.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ImageDataResponseDto {

    private Long id; // 이미지 id
    private String filename; // 원본파일명 = 이미지 이름
    private String base64Image; // base64 인코딩한 이미지 데이터
}
