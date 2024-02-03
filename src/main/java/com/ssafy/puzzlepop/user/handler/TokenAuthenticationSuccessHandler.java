package com.ssafy.puzzlepop.user.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

}