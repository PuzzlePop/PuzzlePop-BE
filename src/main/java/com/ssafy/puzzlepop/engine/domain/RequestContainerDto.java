package com.ssafy.puzzlepop.engine.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class RequestContainerDto {
    private String uuid;
    private Picture picture;
    private User user;
}
