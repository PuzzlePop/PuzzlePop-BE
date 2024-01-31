package com.ssafy.puzzlepop.image.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ImageResponseDto {

    private Long id; // 이미지 id
    private String type; // 이미지 타입(sPuzzle/cPuzzle/item/...)
    private String filename; // 원본파일명 (확장자 제외)
    private String filenameExtension; // 확장자명
    private Long userId; // 업로드한 유저 id

}
