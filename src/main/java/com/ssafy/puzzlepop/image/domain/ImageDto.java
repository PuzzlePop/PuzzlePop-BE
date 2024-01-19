package com.ssafy.puzzlepop.image.domain;

import com.ssafy.puzzlepop.team.domain.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {

    private int id;
    private String code;
    private String name;
    private String filename;
    private String filepath;
    private long filesize;
    private String userId;

    @Builder
    public ImageDto(Image image) {
        this.id = image.getId();
        this.code = image.getCode();
        this.name = image.getName();
        this.filename = image.getFilename();
        this.filepath = image.getFilepath();
        this.filesize = image.getFilesize();
        this.userId = image.getUserId();
    }

    public Image toEntity() {
        return Image.builder()
                .id(this.id)
                .code(this.code)
                .name(this.name)
                .filename(this.filename)
                .filepath(this.filepath)
                .filesize(this.filesize)
                .userId(this.userId)
                .build();
    }

}
