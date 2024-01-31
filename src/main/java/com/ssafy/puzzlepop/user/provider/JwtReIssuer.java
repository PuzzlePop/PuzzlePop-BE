package com.ssafy.puzzlepop.user.provider;


import com.ssafy.puzzlepop.user.domain.TokenDto;
import com.ssafy.puzzlepop.user.exception.InvalidTokenException;
import com.ssafy.puzzlepop.user.exception.RefreshTokenNotFoundException;
import com.ssafy.puzzlepop.user.repository.UserRepository;
import com.ssafy.puzzlepop.user.resolver.JwtResolver;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtReIssuer {

    private final JwtProvider tokenProvider;
    private final JwtResolver tokenResolver;
    private final UserRepository userRepository;


    public TokenDto reissueAccessToken(HttpServletRequest request) throws InvalidTokenException {

        String refreshToken = tokenResolver.resolveTokenOrNull(request);

        if (!tokenProvider.validate(refreshToken) ) {  // 리프레시 토큰 유효성 검사
            throw new InvalidTokenException("유효하지 않은 리프레시 토큰");
        }

        if (!userRepository.existsByRefreshToken(refreshToken)) {  // db 에 리프레시 토큰이 존재하는지 검사
            throw new RefreshTokenNotFoundException();
        }

        return tokenProvider.reissueAccessTokenUsing(refreshToken);  // 액세스 토큰 재발급
    }
}