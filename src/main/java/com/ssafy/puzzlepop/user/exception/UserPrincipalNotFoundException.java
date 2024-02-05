package com.ssafy.puzzlepop.user.exception;

import org.springframework.security.core.AuthenticationException;

public class UserPrincipalNotFoundException extends AuthenticationException {

    public UserPrincipalNotFoundException(String msg) {
        super(msg);
    }
    public UserPrincipalNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
