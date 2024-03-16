package com.opp.todo.security;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponse {
    private String accessToken;
    private String token;
}
