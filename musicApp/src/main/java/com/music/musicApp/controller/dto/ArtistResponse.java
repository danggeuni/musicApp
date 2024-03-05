package com.music.musicApp.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
public class ArtistResponse {
    private Artists artists;
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Artists {
        private List<Items> items;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Items {
            private List<String> genres;
            private List<Images> images;
            private Followers followers;
            private String id;
            private String name;
            @Data
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Images {
                private String url;
            }
            @Data
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Followers {
                private int total;
            }
        }
    }
}