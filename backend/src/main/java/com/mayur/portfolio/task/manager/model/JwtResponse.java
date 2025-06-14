package com.mayur.portfolio.task.manager.model;

import lombok.Getter;
import lombok.Setter;

public class JwtResponse {
    @Getter
    private final String token;
    @Setter
    @Getter
    private String errorMessage = "No errors generating token";

    public JwtResponse(String token) {
        this.token = token;
    }

}
