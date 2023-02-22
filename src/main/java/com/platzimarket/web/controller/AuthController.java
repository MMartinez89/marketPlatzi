package com.platzimarket.web.controller;

import com.platzimarket.domain.Product;
import com.platzimarket.domain.dto.AuthenticationRequest;
import com.platzimarket.domain.dto.AuthenticationResponse;
import com.platzimarket.domain.service.SuperMarketUserDetailsService;
import com.platzimarket.web.security.JWTUtil;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    //@Autowired
    private AuthenticationManager authenticationManager; //Valida si el usuario y la contrasena son validas

    @Autowired
    private SuperMarketUserDetailsService superMarketUserDetailsService;

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> createToken(@RequestBody AuthenticationRequest request){
        try{
            //authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName().toString(), request.getPassword().toString()));

           UserDetails userDetails = superMarketUserDetailsService.loadUserByUsername(request.getUserName());
            String jwt = jwtUtil.generateToken(userDetails);

            return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.OK);
        }catch (BadCredentialsException e){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
