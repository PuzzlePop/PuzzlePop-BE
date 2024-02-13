package com.ssafy.puzzlepop.user.resolver;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.ssafy.puzzlepop.user.cookie.CookieUtils.resolveCookie;

// 요청 헤더로부터 token string 을 꺼내오는 역할을 담당
// Authorization: <type> <credentials> 형태의 헤더
@Component
public class JwtResolver {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    public String resolveTokenOrNull(HttpServletRequest request) {

        Optional<Cookie> cookie = resolveCookie(request, "accessToken");
        String token = null;

        if (cookie.isPresent())
            token = cookie.get().getValue();

        return token;

//        Enumeration<String> headerNames = request.getHeaderNames();
//
//        // 헤더 이름을 반복하며 값을 출력합니다.
//        while (headerNames.hasMoreElements()) {
//            String headerName = headerNames.nextElement();
//            String headerValue = request.getHeader(headerName);
//            System.out.println(headerName + ": " + headerValue);
//        }
//
//        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
//
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer"))
//            return bearerToken.substring(7);
//        else
//            return null;
    }
}