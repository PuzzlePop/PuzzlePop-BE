package com.ssafy.puzzlepop.user.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

}
