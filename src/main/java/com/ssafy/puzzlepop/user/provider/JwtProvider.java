package com.ssafy.puzzlepop.user.provider;

import com.ssafy.puzzlepop.user.domain.PrincipalDetails;
import com.ssafy.puzzlepop.user.domain.TokenDto;
import com.ssafy.puzzlepop.user.domain.User;
import com.ssafy.puzzlepop.user.exception.InvalidTokenException;
import com.ssafy.puzzlepop.user.filter.TokenAuthenticationProcessingFilter;
import com.ssafy.puzzlepop.user.jwtUtils.JwtAuthenticationResult;
import com.ssafy.puzzlepop.user.service.UserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
//    @RefreshScope
@Slf4j
public class JwtProvider {

    private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationProcessingFilter.class);

    private final UserService customUserDetailsService;

    @Value("${jwt.secret}")
    private String secretKey;

    private Key key;

    @Value("${jwt.access-token-validity-in-seconds}")
    private long accessTokenValidTime;

    @Value("${jwt.refresh-token-validity-in-seconds}")
    public long refreshTokenValidTime;

    public static final String jwtType = "Bearer ";

    @PostConstruct
    protected void init() {
        String encodeKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        key = Keys.hmacShaKeyFor(encodeKey.getBytes());
    }

    public String createAccessToken(Authentication authentication) {

        return generateToken(authentication, accessTokenValidTime);
    }

    public String createRefreshToken(Authentication authentication) {

        String token = this.generateToken(authentication, refreshTokenValidTime);
//        refreshTokenRepository.save(new RefreshToken(token));
        return token;
    }

    public String generateToken(Authentication authentication, Long expiration) {

        assert authentication != null;

        User user = ((PrincipalDetails) authentication.getPrincipal()).getUser();

        String provider = user.getProvider();
        String uid = user.getId().toString();
        String email = user.getEmail();

        List<String> authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(uid)
                .claim("provider", provider)
                .claim("email", email)
                .claim("authority", authorities)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }


    public boolean validate(String token) {

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (SignatureException ex) {
            logger.error("JWT signature does not match");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }

    /* web request 에 대한 인증 정보를 반환함. */
    @SuppressWarnings("unchecked")
    public JwtAuthenticationResult decode(String token) throws JwtException {

        Claims claims = this.parseClaims(token);
        String uid = claims.getSubject();
        String provider = claims.get("provider", String.class);
        String email = claims.get("email", String.class);
        List<? extends GrantedAuthority> grantedAuthorities =
                (List<SimpleGrantedAuthority>) claims.get("authority", List.class).stream()
                        .map(authority-> new SimpleGrantedAuthority((String) authority))
                        .collect(Collectors.toList());

        return new JwtAuthenticationResult(uid, provider, email, grantedAuthorities);
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }


    public TokenDto reissueAccessTokenUsing(String refreshToken) throws InvalidTokenException {

        try {
            Claims claims = this.parseClaims(refreshToken);
            return this.createAccessTokenOnly(claims);

        } catch (JwtException e) {
            throw new InvalidTokenException("유효하지 않은 리프레시 토큰. 액세스토큰 재발급 불가");
        }
    }

    private TokenDto createAccessTokenOnly(Claims claims) {
        Date now = new Date();

        String accessToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidTime))
                .signWith(key)
                .compact();

        return TokenDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken("")
                .accessTokenExpireDate(accessTokenValidTime)
                .build();
    }


}
