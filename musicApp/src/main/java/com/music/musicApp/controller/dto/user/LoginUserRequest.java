package com.music.musicApp.controller.dto.user;

import lombok.Data;

@Data
public class LoginUserRequest {
    private String email;
    private String password;
}
