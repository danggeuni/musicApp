package com.music.musicApp.service;

import com.music.musicApp.config.jwt.TokenProvider;
import com.music.musicApp.domain.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    @Autowired
    public TokenService(TokenProvider tokenProvider, RefreshTokenService refreshTokenService, UserService userService) {
        this.tokenProvider = tokenProvider;
        this.refreshTokenService = refreshTokenService;
        this.userService = userService;
    }

    public String createNewAccessToken(String refreshToken) {
        if(!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        String userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        UserEntity user = userService.findById(userId);

        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }
}
