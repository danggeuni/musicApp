package com.music.musicApp.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserEntity {
    private final String userId;
    private final String name;
    private final String password;
    private String nickName;

    public UserEntity update(String nickName) {
        this.nickName = nickName;
        return this;
    }
}
