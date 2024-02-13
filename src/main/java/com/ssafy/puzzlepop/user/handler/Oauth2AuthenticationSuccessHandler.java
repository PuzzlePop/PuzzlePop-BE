package com.ssafy.puzzlepop.user.handler;

import com.ssafy.puzzlepop.user.cookie.CookieUtils;
import com.ssafy.puzzlepop.user.cookie.HttpCookieOAuth2AuthorizationRequestRepository;
import com.ssafy.puzzlepop.user.domain.PrincipalDetails;
import com.ssafy.puzzlepop.user.provider.JwtProvider;
import com.ssafy.puzzlepop.user.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class Oauth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Value("${FRONTEND_URL}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String accessToken = jwtProvider.createAccessToken(authentication);
        String refreshToken = jwtProvider.createRefreshToken(authentication);
        Long id = ((PrincipalDetails) authentication.getPrincipal()).getUser().getId();

        System.out.println("ACCESSTOKEN : "+ accessToken);
        System.out.println("REFRESHTOKEN : "+ refreshToken);


        // JWT 토큰을 response에 담아서 전송
//        response.setContentType("application/json");
//        response.getWriter().write("{\"Authorization\": \"" + "Bearer " + accessToken + "\", \"refreshToken\": \"" + refreshToken + "\"}");
//        HttpSession session = request.getSession();
//        session.setAttribute("userId", id);

        // 응답 헤더에 액세스 토큰 추가
//        response.setHeader("Authorization", "Bearer " + accessToken);
        // REFRESH 토큰
//        UserDto userDto = userService.getUserById(principalDetails.getUser().getId());
        CookieUtils.setCookie(response, "accessToken", accessToken, 18000);
        CookieUtils.setCookie(response, "refreshToken", refreshToken, 18000);
//
//        //코드 내로 리디렉트 설정
        String redirectUrl = frontendUrl;  // 프론트 메인페이지로 이동

        //한국어 인코딩 설정
//        String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8.toString());
//
//        String redirectUrl = "프론트도메인?token=" + token
//                +"&email="+email+"&name="+encodedName;
//        HttpSession session = request.getSession();
//        session.setAttribute("Authorization", accessToken);

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}