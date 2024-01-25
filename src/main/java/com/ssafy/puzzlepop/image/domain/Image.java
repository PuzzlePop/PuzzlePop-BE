package com.ssafy.puzzlepop.image.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

// TODO: @NotNull / @Column(nullable=false) 중 하나로 통일하기

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String type; // 이미지 구분

    @NotNull
    private String name; // 실제 서버에 저장된 파일명 UUID_원본파일명

    @NotNull
    private String filename; // 원본파일명

    @NotNull
    private String filepath; // 서버파일경로/UUID_원본파일명.png

    @NotNull
    private String filenameExtension; // 파일 확장자 png

    @NotNull
    private String userId; // 업로드한 유저 id (기본이미지: admin)

    @CreationTimestamp
    @Column(nullable = false)
    private Date createTime; // 생성시간

    @CreationTimestamp
    @Column(nullable = false)
    private Date updateTime; // 수정시간
}