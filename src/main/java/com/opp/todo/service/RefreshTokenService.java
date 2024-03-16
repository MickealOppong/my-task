package com.opp.todo.service;

import com.opp.todo.repository.RefreshTokenRepository;
import com.opp.todo.repository.TodoUserRepository;
import com.opp.todo.security.RefreshToken;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private RefreshTokenRepository refreshTokenRepository;
    private TodoUserRepository userRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository,
                               TodoUserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    public RefreshToken createRefreshToken(String username){
        RefreshToken refreshToken = RefreshToken.builder()
                .user(userRepository.findByUsername(username).get())
                .token(UUID.randomUUID().toString())
                .expirationTime(Instant.now().plusMillis(300000))
                .build();
       return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpirationTime(RefreshToken refreshToken){
        if(refreshToken.getExpirationTime().isBefore(Instant.now())){
            refreshTokenRepository.delete(refreshToken);
        }
        return refreshToken;
    }

    public RefreshToken removeToken(String token){
        RefreshToken refreshToken= refreshTokenRepository.findByToken(token).get();
        refreshTokenRepository.delete(refreshToken);
        return refreshToken;
    }


}
