package com.ssafy.puzzlepop.user.controller;

import com.ssafy.puzzlepop.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
public class LoginController {

    @Value("${FRONTEND_URL}")
    private String frontendUrl;

    private UserService userService;

    @GetMapping("/login")
    public void getLogin(HttpServletResponse response) throws IOException {
        System.out.println("LOGIN 실행");

        response.sendRedirect(frontendUrl + "api/oauth2/authorization/google");
    }
}
