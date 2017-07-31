package com.springbootsecuritytemplate.security.jwt;



import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.springbootsecuritytemplate.models.Authority;
import com.springbootsecuritytemplate.models.User;


/**
 * @author gonzalo
 *
 */
public final class JwtUserFactory {

    private JwtUserFactory() {}

    /**
     * @param user
     * @return
     */
    public static JwtUser create(User user) {
    	
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getAuthorities()),
                user.getEnabled(),
                user.getLastPasswordResetDate()
        );
        
    }

    /**
     * @param authorities
     * @return
     */
    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities) {
        
    	return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
                .collect(Collectors.toList());
    
    }
}
