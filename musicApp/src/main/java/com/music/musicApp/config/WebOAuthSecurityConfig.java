package com.music.musicApp.config;

import com.music.musicApp.config.jwt.TokenProvider;
import com.music.musicApp.config.oauth.Oauth2UserCustomService;
import com.music.musicApp.domain.repository.RefreshTokenRepository;
import com.music.musicApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebOAuthSecurityConfig {
    private final Oauth2UserCustomService oauth2UserCustomService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

    @Autowired
    public WebOAuthSecurityConfig(Oauth2UserCustomService oauth2UserCustomService, TokenProvider tokenProvider, RefreshTokenRepository refreshTokenRepository, UserService userService) {
        this.oauth2UserCustomService = oauth2UserCustomService;
        this.tokenProvider = tokenProvider;
        this.refreshTokenRepository = refreshTokenRepository;
        this.userService = userService;
    }
}
