package com.springbootsecuritytemplate.security.jwt;



import java.io.Serializable;


/**
 * @author gonzalo
 *
 */
public class  JwtAuthenticationRequest implements Serializable {

    private String username;
    private String password;

    /**
     * 
     */
    public JwtAuthenticationRequest() {
        super();
    }

    /**
     * @param username
     * @param password
     */
    public JwtAuthenticationRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    /**
     * @return
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
