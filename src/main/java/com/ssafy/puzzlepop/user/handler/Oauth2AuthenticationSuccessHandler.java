package com.ssafy.puzzlepop.user.handler;

import com.ssafy.puzzlepop.user.cookie.CookieUtils;
import com.ssafy.puzzlepop.user.domain.PrincipalDetails;
import com.ssafy.puzzlepop.user.provider.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class Oauth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
//    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String accessToken = jwtProvider.createAccessToken(authentication);
        String refreshToken = jwtProvider.createRefreshToken(authentication);
//        String email = principalDetails.getEmail();
//        String name = principalDetails.getUsername();

        // 응답 헤더에 JWT 토큰 추가
        response.addHeader("Authorization", "Bearer " + accessToken);

        // JWT 토큰을 response에 담아서 전송
        response.setContentType("application/json");
        response.getWriter().write("{\"token\": \"" + accessToken + "\"}");
        System.out.println("ACCESSTOKEN : "+ accessToken);
        System.out.println("REFRESHTOKEN : "+ refreshToken);

        CookieUtils.setCookie(response, "accessTokenName", accessToken, 600);
        CookieUtils.setCookie(response, "refreshTokenName", refreshToken, 1800);

        //코드 내로 리디렉트 설정
//        String redirectUrl = "/user/oauth-success?token="+token;
        String redirectUrl = "http://localhost:8080/";  // 프론트로 이동

//        //한국어 인코딩 설정
//        String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8.toString());
//
//        String redirectUrl = "프론트도메인?token=" + token
//                +"&email="+email+"&name="+encodedName;

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }


//    private final AuthProperities authProperties;  // @ConfigurationProperties


//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
//        String targetUrl = this.determineTargetUrl(request, response, authentication);  // 1~3 번 과정 진행
//
//        if (response.isCommitted()) {
//            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
//            return;
//        }
//
//        this.clearAuthenticationAttributes(request);
//        this.httpCookieOAuth2AuthorizationRequestRepository.clearCookies(request, response);
//        this.getRedirectStrategy().sendRedirect(request, response, targetUrl);
//    }
//
//    /**
//     * @param authentication 인증 완료된 결과
//     * @return 인증 결과를 사용해서 access 토큰을 발급하고, 쿠키에 저장되어 있던 redirect_uri(프론트에서 적어준 것)와 합쳐서 반환.
//     * 명시되지 않으면 설정파일({@link AuthProperties})에 명시된 default redirect url 값 적용
//     */
//    @Override
//    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
//
//        String targetUrl = CookieUtils.resolveCookie(request, REDIRECT_URL_PARAM_COOKIE_NAME)  // request 에서 쿠키를 꺼냄
//                .map(Cookie::getValue)
//                .orElse(authProperties.getOauth2().getDefaultRedirectUri()); // 없으면 default 로 설정파일에 기재해 둔 url 사용.
//
//        if (notAuthorized(targetUrl)) {  // redirect forgery 검사 로직.
//            /* 여기서 AuthenticationException 이 발생하면 예외는 AbstractAuthenticationProcessingFilter.doFilter 에서 처리된다.
//             *   - AbstractAuthenticationProcessingFilter.doFilter 안에서 try~ catch~ 에서 잡힘.
//             *   -    -> AbstractAuthenticationProcessingFilter.unsuccessfulAuthentication()
//             *   -    -> Oauth2AuthenticationFailureHandler().onAuthenticationFailure()
//             * */
//            throw new UnauthorizedRedirectUrlException();
//        }
//
//        String imageUrl = OAuth2UserInfoFactory.getOAuth2UserInfo((OAuth2AuthenticationToken) authentication)
//                .getImageUrl();
//
//        return UriComponentsBuilder.fromUriString(targetUrl)
//                .queryParam("access_token", jwtProvider.createAccessToken(authentication))  // 토큰 발급
//                .queryParam("refresh_token", jwtProvider.createRefreshToken(authentication))  // 리프레시 토큰 발급 및 저장
//                .queryParam("expires_in", jwtProvider.getExpiration())
//                .queryParam("image_url", imageUrl)
//                .build().toUriString();
//    }
//
//    private boolean notAuthorized(String redirectUrl) {
//        return !redirectUrl.isBlank() &&
//                !authProperties.getOauth2().isAuthorizedRedirectUri(redirectUrl);  // 설정 파일에 적혀있는 redirect url 목록만 가능하다.
//    }
}