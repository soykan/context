package com.soykan.context;

import com.soykan.context.Blog.BlogUser;
import com.soykan.context.Services.BlogUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private BlogUserService blogUserService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            String username = authentication.getPrincipal().toString();
            String password = authentication.getCredentials().toString();
            System.out.println("USER: " + username + " PASS: " + password);
            BlogUser user = (BlogUser) blogUserService.loadUserByUsername(username);
            System.out.println("USERNAME: " + user.getUsername());
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (user != null) {
                if (passwordEncoder.matches(password, user.getPassword())) {
                    Collection<? extends GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password, authorities);
                    return authenticationToken;
                }
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        throw new BadCredentialsException("Username or password is wrong");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
