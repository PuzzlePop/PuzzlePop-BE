package com.ssafy.puzzlepop.image.domain;

import lombok.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ImageResponseDto {

    private int id; // 이미지 id
    private String type; // 이미지 타입(sPuzzle/cPuzzle/item/...)
    private String filename; // 원본파일명 (확장자 제외)
    private String userId; // 업로드한 유저 id

}
