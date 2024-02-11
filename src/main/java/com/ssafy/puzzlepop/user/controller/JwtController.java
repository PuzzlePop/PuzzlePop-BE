package com.ssafy.puzzlepop.user.controller;

import com.ssafy.puzzlepop.user.cookie.CookieUtils;
import com.ssafy.puzzlepop.user.domain.TokenDto;
import com.ssafy.puzzlepop.user.exception.InvalidTokenException;
import com.ssafy.puzzlepop.user.provider.JwtProvider;
import com.ssafy.puzzlepop.user.provider.JwtReIssuer;
import com.ssafy.puzzlepop.user.resolver.JwtResolver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value="/", method = {RequestMethod.GET, RequestMethod.POST})
@RestController
@RequiredArgsConstructor
public class JwtController {

    private final JwtProvider jwtProvider;
    private final JwtReIssuer jwtReIssuer;
    private final JwtResolver jwtResolver;

    @Value("${jwt.access-token-validity-in-seconds}")
    private long accessTokenValidTime;

    @Value("${jwt.refresh-token-validity-in-seconds}")
    public long refreshTokenValidTime;

    @PostMapping("/token/refresh")
    public ResponseEntity<TokenDto> reissueAccessToken(HttpServletRequest request, HttpServletResponse response) {

        try {
            TokenDto newTokenDto = jwtReIssuer.reissueAccessToken(request);

            CookieUtils.setCookie(response, "accessToken", newTokenDto.getAccessToken(),
                    (int) accessTokenValidTime / 1000);

            return ResponseEntity.status(HttpStatus.OK).body(newTokenDto);

        } catch (InvalidTokenException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @GetMapping("/token/access")
    public ResponseEntity<?> validation(HttpServletRequest request) {
        try {
            Boolean b = jwtProvider.validate(jwtResolver.resolveTokenOrNull(request));
            return ResponseEntity.status(HttpStatus.OK).body(b);
        } catch (InvalidTokenException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}