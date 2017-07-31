package com.springbootsecuritytemplate.security.jwt;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springbootsecuritytemplate.models.User;
import com.springbootsecuritytemplate.security.repository.UserRepository;


/**
 * @author gonzalo
 *
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /* (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
    	User user = userRepository.findByUsername(username);

        if (user == null) {
        	
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
            
        } else {
        	
            return JwtUserFactory.create(user);
            
        }
    }
}
