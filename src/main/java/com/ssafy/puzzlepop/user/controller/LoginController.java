package com.ssafy.puzzlepop.user.controller;

import com.ssafy.puzzlepop.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@AllArgsConstructor
public class LoginController {

    private UserService userService;

    @GetMapping("/login")
    public void getLogin(HttpServletResponse response) throws IOException {
        System.out.println("LOGIN 실행");

        response.sendRedirect("/oauth2/authorization/google");
    }
}
