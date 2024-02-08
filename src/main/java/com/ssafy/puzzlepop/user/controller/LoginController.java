package com.ssafy.puzzlepop.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
//@CrossOrigin("*")
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        System.out.println("LOGIN");
        return "redirect:/oauth2/authorization/google";
    }

}
