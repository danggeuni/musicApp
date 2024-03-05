package com.music.musicApp.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class AlbumResponse {
    private Albums albums;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Albums {
        private List<Items> items;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Items {
            private String id;
            private List<Images> images;
            private String name;
            @JsonProperty("release_date")
            private String releaseDate;

            @Data
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Images {
                private String url;
            }
        }
    }
}
