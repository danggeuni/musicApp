package com.music.musicApp.controller.dto.music;

import lombok.Data;

@Data
public class FavoriteRequest {
    private String trackId;
    private String previewUrl;
}
