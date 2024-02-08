package com.ssafy.puzzlepop.user.controller;

import com.ssafy.puzzlepop.user.domain.TokenDto;
import com.ssafy.puzzlepop.user.exception.InvalidTokenException;
import com.ssafy.puzzlepop.user.provider.JwtProvider;
import com.ssafy.puzzlepop.user.provider.JwtReIssuer;
import com.ssafy.puzzlepop.user.resolver.JwtResolver;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value="/", method = {RequestMethod.GET, RequestMethod.POST})
@RestController
@RequiredArgsConstructor
public class JwtController {

    private final JwtProvider jwtProvider;
    private final JwtReIssuer jwtReIssuer;
    private final JwtResolver jwtResolver;

    @PostMapping("/token/refresh")
//    @Operation(summary = "access token 재발급을 요청한다.", description = "request 헤더 Authenticate 에 refreshToken 넣어서 보내줘야함.")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200"),
//            @ApiResponse(responseCode = "401", description = "유효하지 않은 refreshToken")
//    })
    public ResponseEntity<TokenDto> reissueAccessToken(HttpServletRequest request) throws InvalidTokenException {

        TokenDto newAccessToken = jwtReIssuer.reissueAccessToken(request);

        return ResponseEntity.ok(newAccessToken);
    }


    @GetMapping("/token")
    public ResponseEntity<?> validation(HttpServletRequest request) {
        System.out.println("validating");
        Boolean b = jwtProvider.validate(jwtResolver.resolveTokenOrNull(request));
        System.out.println(b);
        return ResponseEntity.ok(b);
    }
}