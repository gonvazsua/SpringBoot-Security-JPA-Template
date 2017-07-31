package com.springbootsecuritytemplate.security.jwt;



import java.io.Serializable;


/**
 * @author gonzalo
 *
 */
public class JwtAuthenticationResponse implements Serializable {

    private final String token;

    /**
     * @param token
     */
    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }

    /**
     * @return
     */
    public String getToken() {
        return this.token;
    }
}
