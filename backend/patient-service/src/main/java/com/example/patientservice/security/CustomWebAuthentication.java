package com.example.patientservice.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class CustomWebAuthentication extends WebAuthenticationDetails {
    private final String jwtToken;

    public CustomWebAuthentication(HttpServletRequest request, String jwtToken) {
        super(request);
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }
}
