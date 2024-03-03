package com.music.musicApp.controller.dto.music;

import com.music.musicApp.domain.entity.FavoriteEntity;
import lombok.Data;

@Data
public class FavoriteResponse {
    private String trackId;
    private String previewUrl;

    public FavoriteResponse(FavoriteEntity entity){
        this.trackId = entity.getTrackId();
        this.previewUrl = entity.getPreviewUrl();
    }
}
