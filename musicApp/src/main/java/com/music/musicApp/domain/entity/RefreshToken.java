package com.music.musicApp.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class RefreshToken {
    private final Long id;
    private final String userId;
    private String refreshToken;

    public RefreshToken(Long id, String userId, String refreshToken) {
        this.id = id;
        this.userId = userId;
        this.refreshToken = refreshToken;
    }

    public RefreshToken update(String newRefreshToken) {
        this.refreshToken = newRefreshToken;
        return this;
    }
}
