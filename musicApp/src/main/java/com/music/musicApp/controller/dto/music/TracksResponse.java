package com.music.musicApp.controller.dto.music;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TracksResponse {
    private List<Items> items;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Items {
        private String name;

        @JsonProperty("preview_url")
        private String previewUrl;
        private String id;
    }
}
