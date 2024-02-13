package com.ssafy.puzzlepop.user.jwtUtils;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class JwtAuthenticationResult extends AbstractAuthenticationToken {

    private Object principal;
    private final String uid;
    private final String provider;
    private final String email;

    /**
     * Creates a token with the supplied array of authorities.
     *
     * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal
     *                    represented by this authentication object.
     */
    public JwtAuthenticationResult(String uid, String provider, String email, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.uid = uid;
        this.provider = provider;
        this.email = email;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
