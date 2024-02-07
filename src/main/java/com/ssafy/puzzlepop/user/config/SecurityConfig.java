package com.ssafy.puzzlepop.user.config;

import com.ssafy.puzzlepop.user.cookie.HttpCookieOAuth2AuthorizationRequestRepository;
import com.ssafy.puzzlepop.user.filter.TokenAuthenticationProcessingFilter;
import com.ssafy.puzzlepop.user.handler.Oauth2AuthenticationFailureHandler;
import com.ssafy.puzzlepop.user.handler.Oauth2AuthenticationSuccessHandler;
import com.ssafy.puzzlepop.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserService userService;
    private final Oauth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler;
    private final Oauth2AuthenticationFailureHandler oauth2AuthenticationFailureHandler;
    private final TokenAuthenticationProcessingFilter tokenAuthenticationProcessingFilter;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    private String frontendUrl = "http://localhost:5173";

    public SecurityConfig(UserService userService, Oauth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler, Oauth2AuthenticationFailureHandler oauth2AuthenticationFailureHandler, TokenAuthenticationProcessingFilter tokenAuthenticationProcessingFilter, HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository) {
        this.userService = userService;
        this.oauth2AuthenticationSuccessHandler = oauth2AuthenticationSuccessHandler;
        this.oauth2AuthenticationFailureHandler = oauth2AuthenticationFailureHandler;
        this.tokenAuthenticationProcessingFilter = tokenAuthenticationProcessingFilter;
        this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

//        http.cors();
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));
        http
                .csrf(AbstractHttpConfigurer::disable);
//                .sessionManagement();

        http
                .formLogin((login) -> login.disable());

        http
                .httpBasic((basic) -> basic.disable());

        http
                .oauth2Login((oauth2) -> oauth2
                        .loginPage("/login")
                        .authorizationEndpoint(auth -> auth
                                .baseUri("/oauth2/authorization")
                                .authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository))
                        .userInfoEndpoint((userInfoEndpointConfig) ->
                                userInfoEndpointConfig.userService(userService))
                        .successHandler(oauth2AuthenticationSuccessHandler)
                        .failureHandler(oauth2AuthenticationFailureHandler)
                );

        http
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
//                        .deleteCookies("accessTokenName", "refreshTokenName")
                );


        http
                .authorizeHttpRequests((authorize) -> authorize
                                .anyRequest().permitAll()
//                        .requestMatchers("/","/login/**").permitAll()

//                        .anyRequest().authenticated()
                );

        http
                .addFilterBefore(tokenAuthenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of(frontendUrl));
        configuration.addAllowedOriginPattern("http://localhost:5173");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        configuration.addExposedHeader("Authorization");
//        configuration.addExposedHeader();

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", configuration);
        return urlBasedCorsConfigurationSource;
    }
}