package com.insuranceManagement.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfiguration {
//    Defines a bean for providing user details for authentication.

    @Bean
    public UserDetailsService userDetailsService() {
//         Creates a user with username "test@sunbasedata.com", encoded password, and
//         role "ADMIN"
//         this is for login
        UserDetails userDetails = User.builder()
                .username("admin").password(passwordEncoder().encode("DriveSoft@@!"))
                .roles("ADMIN").build();

        UserDetails userDetails1 = User.builder()
                .username("testerAPI@drivesoft.tech").password(passwordEncoder().encode("HeoVIaCST3st@@Main"))
                .roles("ADMIN").build();

        UserDetails userDetails2 = User.builder().
                username("Anmol@26").password(passwordEncoder().encode("anmol26"))
                .roles("ADMIN").build();

        return new InMemoryUserDetailsManager(userDetails);
    }

//    Defines a bean for password encoding.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    Defines a bean for providing the authentication manager.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
