package com.ssafy.puzzlepop.user.config;

import com.ssafy.puzzlepop.user.filter.TokenAuthenticationProcessingFilter;
import com.ssafy.puzzlepop.user.handler.Oauth2AuthenticationFailureHandler;
import com.ssafy.puzzlepop.user.handler.Oauth2AuthenticationSuccessHandler;
import com.ssafy.puzzlepop.user.jwtUtils.HttpCookieOAuth2AuthorizationRequestRepository;
import com.ssafy.puzzlepop.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserService userService;
    private final Oauth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler;
    private final Oauth2AuthenticationFailureHandler oauth2AuthenticationFailureHandler;
    private final TokenAuthenticationProcessingFilter tokenAuthenticationProcessingFilter;


    public SecurityConfig(UserService userService, Oauth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler, Oauth2AuthenticationFailureHandler oauth2AuthenticationFailureHandler, TokenAuthenticationProcessingFilter tokenAuthenticationProcessingFilter) {
        this.userService = userService;
        this.oauth2AuthenticationSuccessHandler = oauth2AuthenticationSuccessHandler;
        this.oauth2AuthenticationFailureHandler = oauth2AuthenticationFailureHandler;
        this.tokenAuthenticationProcessingFilter = tokenAuthenticationProcessingFilter;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf((csrf) -> csrf.disable());
//                .sessionManagement();

        http
                .formLogin((login) -> login.disable());

        http
                .httpBasic((basic) -> basic.disable());

        http
                .oauth2Login((oauth2) -> oauth2
                        .loginPage("/login")
//                        .authorizationEndpoint(auth -> auth
//                                .baseUri("/login/oauth2/authorization")
//                                .authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository))
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
                        .deleteCookies("accessTokenName", "refreshTokenName")
                );


//        http
//                .authorizeHttpRequests((authorize) -> authorize
//                        .requestMatchers("/","/login/**").permitAll()
//                        .anyRequest().authenticated()
//                );
//
//        http
//                .addFilterBefore(tokenAuthenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}