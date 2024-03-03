package com.music.musicApp.controller.dto.user;

import lombok.Data;

@Data
public class LoginUserRequest {
    private String userId;
    private String password;
}
