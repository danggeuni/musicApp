package com.music.musicApp.controller.dto.user;

import com.music.musicApp.domain.entity.UserEntity;
import lombok.Data;

@Data
public class AddUserRequest {
    private String email;
    private String name;
    private String password;

    public UserEntity toEntity() {
        return new UserEntity(null, email, name, password);
    }
}
