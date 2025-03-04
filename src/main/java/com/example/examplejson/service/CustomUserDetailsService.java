package com.example.examplejson.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Loading user: " + username);

        if ("oybek".equals(username)) {
            return User.builder()
                    .username("oybek")
                    .password(new BCryptPasswordEncoder().encode("oybekuz"))
                    .roles("USER")
                    .build();
        }

        throw new UsernameNotFoundException("User not found: " + username);
    }
}
