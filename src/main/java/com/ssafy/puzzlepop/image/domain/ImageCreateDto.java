package com.ssafy.puzzlepop.image.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ImageCreateDto {

    private String type;
    private String userId;

}
