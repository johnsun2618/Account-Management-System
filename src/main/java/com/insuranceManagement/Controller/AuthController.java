package com.insuranceManagement.Controller;

import com.insuranceManagement.JWTAuthentication.JwtRequest;
import com.insuranceManagement.JWTAuthentication.JwtResponse;
import com.insuranceManagement.Security.JwtHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private JwtHelper helper;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);


    @PostMapping(path = "/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
        logger.info("Login request received for email: {}", request.getEmail());
//         Authenticate user credentials.
        this.doAuthenticate(request.getEmail(), request.getPassword());
//         Retrieve user details and generate JWT token.
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(userDetails);
//         Build and return JWT response.
        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .username(userDetails.getUsername()).build();
        logger.info("Generated JWT Response: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    //     Helper method for authenticating user credentials.
    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new
                UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);
//             Throw exception for invalid credentials if login details is invalid
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }

//    Exception handler for BadCredentialsException.
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> exceptionHandler() {
        return new ResponseEntity("Invalid email and password", HttpStatus.BAD_REQUEST);
    }

}
