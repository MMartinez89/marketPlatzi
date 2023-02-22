package com.platzimarket.web.security;

import com.platzimarket.domain.service.SuperMarketUserDetailsService;
import com.platzimarket.web.security.filter.JwtFilterRequest;
import net.bytebuddy.build.Plugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.print.DocFlavor;

import java.util.ArrayList;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SuperMarketUserDetailsService userDetailsService;

    @Autowired
    private JwtFilterRequest jwtFilterRequest;


   /*@Bean
    SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager auhtManager) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests()
                .anyRequest().authenticated().and().httpBasic().and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .build();
   }*/

    @Bean
     SecurityFilterChain  filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/auth/authenticate").permitAll()
                        .anyRequest().authenticated());//.addFilterBefore(jwtFilterRequest, UsernamePasswordAuthenticationFilter.class);

        return http.addFilterBefore(jwtFilterRequest, UsernamePasswordAuthenticationFilter.class).build();
        //return http.build();
    }



    @Bean
    UserDetailsService userDetailsServices(){
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("manuel")
                .password(passwordEncoder().encode("1234"))
                .roles()
                .build());
        return manager;
   }

  /* @Bean
   AuthenticationManager authManager(HttpSecurity http) throws Exception {

       return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and().build();
   }*/

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {

       return   http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                //.passwordEncoder(passwordEncoder())
                .and()
               .build();


    }

   @Bean
    PasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder();
   }







    // @Bean
    /*SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        return http
                .csrf().disable()
                .authorizeHttpRequests()
                .anyRequest().
                authenticated()
                .and().
                httpBasic().
                and().
                sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .build();
    }*/



}
