package com.ssafy.puzzlepop.user.config;

import com.ssafy.puzzlepop.user.handler.Oauth2AuthenticationFailureHandler;
import com.ssafy.puzzlepop.user.handler.Oauth2AuthenticationSuccessHandler;
import com.ssafy.puzzlepop.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;
//    private final Oauth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler;
    private final Oauth2AuthenticationFailureHandler oauth2AuthenticationFailureHandler;

    public SecurityConfig(UserService userService, Oauth2AuthenticationFailureHandler oauth2AuthenticationFailureHandler) {
        this.userService = userService;
        this.oauth2AuthenticationFailureHandler = oauth2AuthenticationFailureHandler;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf((csrf) -> csrf.disable());

        http
                .formLogin((login) -> login.disable());

        http
                .httpBasic((basic) -> basic.disable());

        http
                .oauth2Login((oauth2) -> oauth2
                        .loginPage("/login")
                                .userInfoEndpoint((userInfoEndpointConfig) ->
                                        userInfoEndpointConfig.userService(userService))
//                                .successHandler(oauth2AuthenticationSuccessHandler)
//                                .failureHandler(oauth2AuthenticationFailureHandler)
                        );

//        http
//                .authorizeHttpRequests((auth) -> auth
//                        .requestMatchers("/login/*").permitAll()
//                        .anyRequest().authenticated());


        return http.build();
    }
}