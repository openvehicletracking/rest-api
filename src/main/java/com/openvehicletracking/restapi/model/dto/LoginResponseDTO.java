package com.openvehicletracking.restapi.model.dto;

import com.openvehicletracking.restapi.model.user.User;
import org.springframework.security.core.Authentication;

public class LoginResponseDTO {

    private String token;
    private User user;

    public LoginResponseDTO(Authentication authentication, String token) {
        this.token = token;
        this.user = (User) authentication.getPrincipal();
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }
}

