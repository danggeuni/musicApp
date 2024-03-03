package com.music.musicApp.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FavoriteEntity {
    private Long id;
    private String trackId;
    private String previewUrl;
    private String userId;
}
