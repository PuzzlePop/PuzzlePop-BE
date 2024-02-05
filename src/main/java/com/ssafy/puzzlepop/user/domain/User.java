package com.ssafy.puzzlepop.user.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.sql.Timestamp;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    private String nickname;
    // @Column(unique = true, length = 50)
    private String email;

    @Column(length = 50)
    private String givenName;
    @Column(length = 50)
    private String familyName;
    @Column(length = 50)
    private String locale;
    @Column(length = 1024)
    private String imgPath;

    @ColumnDefault("true")
    private Boolean bgm;
    @ColumnDefault("true")
    private Boolean soundEffect;

    private int playingGameID;
    @ColumnDefault("0")
    private int gold;
    @Column(length = 32)
    private String onlineStatus;

    /////////////////
    private String accessToken;
    private String refreshToken;
    private String tokenType;

    private String provider;
    private String providerId;
    private String role;

    private Timestamp createdDate;
    private Timestamp expiredDate;

//    private OAuth2UserInfo oAuth2UserInfo;
//
//    public User(OAuth2UserInfo oAuth2UserInfo) {
//        this.oAuth2UserInfo = oAuth2UserInfo;
//    }
//    public User(OAuth2UserInfo oAuth2UserInfo, String role)  {
//        this.oAuth2UserInfo = oAuth2UserInfo;
//        this.role = role;
//    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void update(UserDto userDto) {
        this.id = userDto.getId();
        this.email = userDto.getEmail();
        this.nickname = userDto.getNickname();
        this.givenName = userDto.getGivenName();
        this.familyName = userDto.getFamilyName();
        this.imgPath = userDto.getImgPath();
        this.locale = userDto.getLocale();
        this.bgm = userDto.getBgm();
        this.soundEffect = userDto.getSoundEffect();
        this.playingGameID = userDto.getPlayingGameID();
        this.gold = userDto.getGold();
        this.onlineStatus = userDto.getOnlineStatus();
        this.accessToken = userDto.getAccessToken();
        this.refreshToken = userDto.getRefreshToken();
        this.tokenType = userDto.getTokenType();
        this.createdDate = userDto.getCreatedDate();
        this.expiredDate = userDto.getExpiredDate();
        this.role = userDto.getRole();
        this.provider = userDto.getProvider();
        this.providerId = userDto.getProviderId();
    }
}
