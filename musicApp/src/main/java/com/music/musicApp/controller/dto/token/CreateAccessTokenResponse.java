package com.music.musicApp.controller.dto.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccessTokenResponse {
    private String accessToken;

    public CreateAccessTokenResponse(String accessToken){
        this.accessToken = accessToken;
    }
}
