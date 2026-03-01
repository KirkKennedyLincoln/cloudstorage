package com.udacity.jwdnd.course1.cloudstorage.services;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.models.User;

@Service
public class AuthenticationService implements AuthenticationProvider {
    private UserService userService;
    private HashService hashService;

    public AuthenticationService(UserService userService, HashService hashService) {
        this.userService = userService;
        this.hashService = hashService;
    }

    @Override
    public Authentication authenticate(
            org.springframework.security.core.Authentication authentication
    ) {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        System.out.println(password);
        User found = this.userService.getUserByUsername(username);
        if (null == found) {
            throw new BadCredentialsException(username);
        }
        System.out.println(password);

        String tempPass = this.hashService.getHashedValue(password, found.getSalt());
        if (!found.getPassword().equals(tempPass)) {
            throw new BadCredentialsException("Second throw: " + username);
        }
        System.out.println(password);
        return new UsernamePasswordAuthenticationToken(found, tempPass, AuthorityUtils.NO_AUTHORITIES);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
