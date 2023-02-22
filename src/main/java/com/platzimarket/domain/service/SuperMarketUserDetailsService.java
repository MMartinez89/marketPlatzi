package com.platzimarket.domain.service;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public  class SuperMarketUserDetailsService implements UserDetailsService {
    @Override()
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User("manuel", "{noop}1234", new ArrayList<>());
    }

}
