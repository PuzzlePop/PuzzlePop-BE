//package com.ssafy.puzzlepop.user.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@EnableWebMvc
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowCredentials(true) // 쿠키 인증 요청 허용
//                .allowedOriginPatterns("*") // 허용할 출처
//                .allowedHeaders("*")
//                .allowedMethods("GET", "POST") // 허용할 HTTP method
//                .maxAge(3000); // 원하는 시간만큼 pre-flight 리퀘스트를 캐싱
//    }
//}