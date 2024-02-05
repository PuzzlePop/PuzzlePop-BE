package com.ssafy.puzzlepop.user.resolver;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

// 요청 헤더로부터 token string 을 꺼내오는 역할을 담당
// Authorization: <type> <credentials> 형태의 헤더
@Component
public class JwtResolver {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    public String resolveTokenOrNull(HttpServletRequest request) {

        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
            return bearerToken.substring(7);
        else
            return null;
    }
}