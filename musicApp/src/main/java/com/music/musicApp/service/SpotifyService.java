package com.music.musicApp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.music.musicApp.controller.dto.AlbumResponse;
import com.music.musicApp.controller.dto.ArtistResponse;
import com.music.musicApp.controller.dto.TracksResponse;
import com.music.musicApp.utils.CreateTokenRT;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class SpotifyService {
    CreateTokenRT tokenRT = new CreateTokenRT();
    String ARTIST_SEARCH_URI = "https://api.spotify.com/v1/search?type=artist&limit=1&q=";
    String ALBUM_SEARCH_URI = "https://api.spotify.com/v1/search?type=album&q=";
    String TRACKS_SEARCH_URI = "https://api.spotify.com/v1/albums/";

    private final String usableToken = tokenRT.getAccessToken();

    public ArtistResponse findQuery(String searchQuery) {
        String encodedSearchQuery = URLEncoder.encode(searchQuery, StandardCharsets.UTF_8);

        HttpGet get = new HttpGet(ARTIST_SEARCH_URI + encodedSearchQuery);
        get.setHeader("Authorization", "Bearer  " + usableToken);
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute(get);

            String json = EntityUtils.toString(response.getEntity());
            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(json, ArtistResponse.class);
        } catch (IOException e) {
            throw new RuntimeException("겟요청 실패함요;", e);
        }
    }

    public AlbumResponse findAlbum(String searchQuery) {
        String encodedSearchQuery = URLEncoder.encode(searchQuery, StandardCharsets.UTF_8);

        HttpGet get = new HttpGet(ALBUM_SEARCH_URI + encodedSearchQuery);
        get.setHeader("Authorization", "Bearer  " + usableToken);
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute(get);

            String json = EntityUtils.toString(response.getEntity());
            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(json, AlbumResponse.class);
        } catch (IOException e) {
            throw new RuntimeException("겟요청 실패함요;", e);
        }
    }

    public TracksResponse showTracks(String albumId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer  " + usableToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(TRACKS_SEARCH_URI + albumId + "/tracks", HttpMethod.GET, entity, String.class);
        String result = response.getBody();
        System.out.println(result);
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(result, TracksResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("노래 목록 불러오는데 실패함요", e);
        }
    }
}
