package com.ssafy.puzzlepop.user.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserInfoDto {

    private Long id;
    private String email;
    private String nickname; // familyName+givenName
    private String imgPath;
    private String locale;
    private String familyName;
    private String givenName;

    private Integer playingGameID;
    private String onlineStatus;
}