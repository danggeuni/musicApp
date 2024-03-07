package com.music.musicApp.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;
import java.util.List;

@Getter
@AllArgsConstructor
public class UserEntity {
    private final Long id;
    private final String email;
    private final String name;
    private final String password;
}
