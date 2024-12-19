package com.insuranceManagement.JWTAuthentication;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtRequest {
//    The email associated with the authentication request.
    private String email;
//    The password associated with the authentication request.
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
