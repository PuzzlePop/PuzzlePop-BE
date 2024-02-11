package com.ssafy.puzzlepop.user.service;

import com.ssafy.puzzlepop.user.domain.PrincipalDetails;
import com.ssafy.puzzlepop.user.domain.User;
import com.ssafy.puzzlepop.user.domain.UserDto;
import com.ssafy.puzzlepop.user.exception.UserNotFoundException;
import com.ssafy.puzzlepop.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override // DefaultOAuth2UserService에서 오버라이드
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User.getAttributes());

        String provider = userRequest.getClientRegistration().getRegistrationId();

        // 회원가입 강제 진행
//        if (provider.equals("google")) {
//            System.out.println("구글 로그인 요청");
//        } else {
//            System.out.println("우리는 구글만 지원합니다.");
//        }

        String providerId = oAuth2User.getAttribute("sub");
//        String provider = "";
        String email = oAuth2User.getAttribute("email");
//        String username = oAuth2User.getAttribute("name");
//        String password = "OAuth2"; //Oauth2로 로그인 시, 패스워드는 의미없음.
        String role = "ROLE_USER";

        String nickname = "닉네임";
        String givenName = oAuth2User.getAttribute("given_name");
        String familyName = oAuth2User.getAttribute("family_name");
        String imgPath = oAuth2User.getAttribute("picture");
        String locale = oAuth2User.getAttribute("locale");
        boolean bgm = true;
        boolean soundEffect = true;
        Integer playingGameID = 0;
        Integer gold = 0;
        String onlineStatus = "online";

        Optional<User> existData = userRepository.findByEmail(email);

        if (existData.isEmpty()) {

            User newUser = User.builder()
                    .id(0L)
                    .email(email)
                    .nickname(nickname)
                    .givenName(givenName)
                    .familyName(familyName)
                    .imgPath(imgPath)
                    .locale(locale)
                    .bgm(bgm)
                    .soundEffect(soundEffect)
                    .playingGameID(playingGameID)
                    .gold(gold)
                    .onlineStatus(onlineStatus)

                    .provider(provider)
                    .providerId(providerId)
                    .role(role)
                    .build();

            System.out.println(newUser.toString());
            userRepository.save(newUser);

            existData = Optional.of(newUser);
        }
        return new PrincipalDetails(existData.get(), oAuth2User.getAttributes());
    }




//        OAuth2User oauth2User = super.loadUser(userRequest);
//
//        // 구글 로그인 버튼 클릭 -> 로그인 창 -> 로그인 완료 -> code 리턴(Oauth-Client 라이브러리) -> AccessToken 요청
//        // userRequest 정보 -> loadUser 함수 호출 -> 구글로부터 회원 프로필 받아준다.
//        System.out.println(super.loadUser(userRequest).getAttributes());
//
//        // 회원가입 강제 진행
//        OAuth2UserInfo oAuth2UserInfo = null;
//        String platform = userRequest.getClientRegistration().getRegistrationId();
//
//        if (platform.equals("google")) {
//
//            System.out.println("구글 로그인 요청");
//            oAuth2UserInfo = new GoogleUserInfo(oauth2User.getAttributes());
////        } else if (platform.equals("facebook")) {
////
////            System.out.println("페이스북 로그인 요청");
////            oAuth2UserInfo = new FacebookUserInfo(oauth2User.getAttributes());
////        } else if (platform.equals("naver")) {
////
////            System.out.println("네이버 로그인 요청");
////            oAuth2UserInfo = new NaverUserInfo(oauth2User.getAttributes());
//        } else {
//            System.out.println("우리는 구글만 지원합니다.");
//        }
//
////        String provider = oAuth2UserInfo.getProvider();
////        String providerId = oAuth2UserInfo.getProviderId();
////        String username = provider + "_" + providerId;
////        String password = bCryptPasswordEncoder.encode("안녕하세요");
////        String email = oAuth2UserInfo.getEmail();
////        String role = "ROLE_USER";
//
//        //////////////////////////////////////////
//        /////////// 초기 정보 입력 필요 /////////////
//
//        String email = oAuth2UserInfo.getEmail();
//        String nickname = "닉네임";
//        String givenName = oAuth2UserInfo.getGivenName();
//        String familyName = oAuth2UserInfo.getFamilyName();
//        String imgPath = oAuth2UserInfo.getPicture();
//        String locale = oAuth2UserInfo.getLocale();
//
//        boolean bgm = true;
//        boolean soundEffect = true;
//        int playingGameID = -1;
//        int gold = 0;
//        String onlineStatus = "online";
//
//        ////////// 토큰 이거.... JWT 써야하는지? /////////
//        String accessToken = userRequest.getAccessToken().getTokenValue();
//        String refreshToken = "refreshtoken";
//        String tokenType = "accessTokenType";
//
//        // 일단
//        Timestamp createdDate = oAuth2UserInfo.getIat();
////        System.out.println(userRequest.getAccessToken().getIssuedAt());
//        Timestamp expiredDate = oAuth2UserInfo.getExp();
////        System.out.println(userRequest.getAccessToken().getExpiresAt());
//        String provider = oAuth2UserInfo.getProvider();
//        String providerId = oAuth2UserInfo.getProviderId();
//        String role = "ROLE_USER";
//
//
//        User findUser = userRepository.findByEmail(email);
//        if (findUser == null) {
//
//            findUser = User.builder()
//
////                    .id(id)  // 없어도 됨
//                    .email(email)
//                    .nickname(nickname)
//                    .givenName(givenName)
//                    .familyName(familyName)
//                    .imgPath(imgPath)
//                    .locale(locale)
//                    .bgm(bgm)
//                    .soundEffect(soundEffect)
//                    .playingGameID(playingGameID)
//                    .gold(gold)
//                    .onlineStatus(onlineStatus)
//                    .accessToken(accessToken)
//                    .refreshToken(refreshToken)
//                    .tokenType(tokenType)
//                    .createdDate(createdDate)
//                    .expiredDate(expiredDate)
//
//                    .provider(provider)
//                    .providerId(providerId)
//                    .role(role)
//
//                    .build();
//
//            userRepository.save(findUser);
//        }
//        /////////////////////////////////////////


    public List<UserDto> getAllUsers() {
        // 전체 유저들 목록 반환
        List<User> users = userRepository.findAll();
        return users.stream().map(UserDto::new).collect(Collectors.toList());
    }

    public UserDto getUserById(Long id) {
        // 해당 id를 가진 유저를 반환
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + id)
        );
        return new UserDto(user);
    }

    public UserDto getUserByEmail(String email) {
        // 해당 email을 가진 유저를 반환
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User not found with email: " + email)
        );
        return new UserDto(user);
    }

    public UserDto getUserByIdAndEmail(Long id, String email) {
        // 해당 id를 가진 유저를 반환
        User user = userRepository.findByIdAndEmail(id, email).orElseThrow(
                () -> new UserNotFoundException("User not found with id " + id + " with " + email)
        );
        return new UserDto(user);
    }

    public List<UserDto> getUsersByNickname(String nickname) {
        // 해당 닉네임을 가진 유저들 목록 반환
        List<User> users = userRepository.findAllByNickname(nickname);
//        .orElseThrow(
//                () -> new UserRuntimeException("Users not found with nickname: " + nickname)
//        );
        return users.stream().map(UserDto::new).collect(Collectors.toList());
//        return new users.stream().map(UserDto::new).collect(Collectors.toList());
    }

    public Long createUser(UserDto requestDto) {
        User user = userRepository.save(requestDto.toEntity());
        return user.getId();
    }

    public Long updateUser(UserDto requestDto) {
        User user = userRepository.findById(requestDto.getId()).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + requestDto.getId()));
        user.update(requestDto);
        return userRepository.save(user).getId();
    }

    public void deleteUser(UserDto requestDto) {
        User user = userRepository.findById(requestDto.getId()).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + requestDto.getId()));
        userRepository.delete(user);
    }
}