package com.ssafy.puzzlepop.user.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserDto {
    private Long id;
    private String email;
    private String nickname;
    private String givenName;
    private String familyName;
    private String imgPath;
    private String locale;
    private Boolean bgm;
    private Boolean soundEffect;
    private Integer playingGameID;
    private Integer gold;
    private String onlineStatus;

    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Date createdDate;
    private Date expiredDate;

    private String provider;
    private String providerId;
    private String role;

    @Builder
    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.givenName = user.getGivenName();
        this.familyName = user.getFamilyName();
        this.imgPath = user.getImgPath();
        this.locale = user.getLocale();
        this.bgm = user.getBgm();
        this.soundEffect = user.getSoundEffect();
        this.playingGameID = user.getPlayingGameID();
        this.gold = user.getGold();
        this.onlineStatus = user.getOnlineStatus();

        this.accessToken = user.getAccessToken();
        this.refreshToken = user.getRefreshToken();
        this.tokenType = user.getTokenType();
        this.createdDate = user.getCreatedDate();
        this.expiredDate = user.getExpiredDate();

        this.provider = user.getProvider();
        this.providerId = user.getProviderId();
        this.role = user.getRole();
    }

    public User toEntity() {

        return User.builder()
                .id(this.id)
                .email(this.email)
                .nickname(this.nickname)
                .givenName(this.givenName)
                .familyName(this.familyName)
                .imgPath(this.imgPath)
                .locale(this.locale)
                .bgm(this.bgm)
                .soundEffect(this.soundEffect)
                .playingGameID(this.playingGameID)
                .gold(this.gold)
                .onlineStatus(this.onlineStatus)

                .accessToken(this.accessToken)
                .refreshToken(this.refreshToken)
                .tokenType(this.tokenType)
                .createdDate(this.createdDate)
                .expiredDate(this.expiredDate)

                .provider(this.provider)
                .providerId(this.providerId)
                .role(this.role)
                .build();
    }
}
