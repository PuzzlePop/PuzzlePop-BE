package com.ssafy.puzzlepop.image.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ssafy.puzzlepop.team.domain.Team;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ImageDto {

    private Long id;
    private String type;
    private String name;
    private String filename;
    private String filepath;
    private String filenameExtension;
    private Long userId;
    private Date createTime;
    private Date updateTime;

    @Builder
    public ImageDto(Image image) {
        this.id = image.getId();
        this.type = image.getType();
        this.name = image.getName();
        this.filename = image.getFilename();
        this.filepath = image.getFilepath();
        this.filenameExtension = image.getFilenameExtension();
        this.userId = image.getUserId();
        this.createTime = image.getCreateTime();
        this.updateTime = image.getUpdateTime();
    }

    public Image toEntity() {
        return Image.builder()
                .id(this.id)
                .type(this.type)
                .name(this.name)
                .filename(this.filename)
                .filepath(this.filepath)
                .filenameExtension(this.filenameExtension)
                .userId(this.userId)
                .createTime(this.createTime)
                .updateTime(this.updateTime)
                .build();
    }

}
