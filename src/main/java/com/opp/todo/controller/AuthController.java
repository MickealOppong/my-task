package com.opp.todo.controller;

import com.opp.todo.security.AuthRequest;
import com.opp.todo.security.JwtResponse;
import com.opp.todo.security.RefreshToken;
import com.opp.todo.security.RefreshTokenRequest;
import com.opp.todo.service.RefreshTokenService;
import com.opp.todo.service.TodoTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationManager authManager;
    private TodoTokenService todoTokenService;
    private RefreshTokenService refreshTokenService;

    public AuthController(AuthenticationManager authManager, TodoTokenService todoTokenService,
                          RefreshTokenService refreshTokenService) {
        this.authManager = authManager;
        this.todoTokenService = todoTokenService;
        this.refreshTokenService = refreshTokenService;
    }


    @GetMapping
    public String home(){
        return "hello, this is authentication endpoint";
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest) {

       Authentication authentication = authManager
               .authenticate(new UsernamePasswordAuthenticationToken(authRequest.username(),authRequest
                       .password()));
    return todoTokenService.token(authentication);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> createToken(@RequestBody AuthRequest authRequest){

        try{
            Authentication authentication = authManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authRequest.username(),authRequest
                            .password()));
            if(authentication.isAuthenticated()){
                RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.username());
                JwtResponse jwtResponse = JwtResponse.builder()
                        .accessToken(todoTokenService.token(authentication))
                        .token(refreshToken.getToken()).build();
                return ResponseEntity.ok().body(jwtResponse);
            }
        }catch (Exception e){

        }


        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/refreshToken")
    public JwtResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return refreshTokenService.findToken(refreshTokenRequest.token())
                .map(refreshTokenService::verifyExpirationTime)
                .map(RefreshToken::getUser)
                .map(todoUser -> {
                    String accessToken = todoTokenService.token(todoUser.getUsername());
                    return JwtResponse.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequest.token())
                            .build();
                }).get();
    }



    @DeleteMapping("/logout")
    public ResponseEntity<String> removeToken(@RequestParam String token){
       RefreshToken refreshToken = refreshTokenService.removeToken(token);
        if(token !=null){
            return ResponseEntity.ok().body("Token deleted");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Was not able to delete token");
    }
}
