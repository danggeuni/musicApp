package com.music.musicApp.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class UserEntity {
    private final String userId;
    private final String name;
    private final String password;
    private final String token;

    public UserEntity(String userId, String name, String password, String code) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.token = code;
    }

    public UserEntity(String userId, String name, String password) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.token = null;
    }
}
