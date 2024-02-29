package com.music.musicApp.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class CreateTokenRT {
    private static final String CLIENT_ID = "bf42d193c4d6484e9a1fd979ca8d78e2";
    private static final String CLIENT_SECRET = "081f145afdaf46ecb159b01694cb0e67";
    private final static String TOKEN_URI = "https://accounts.spotify.com/api/token";

    public String getAccessToken() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> requestEntity = new HttpEntity<>("grant_type=client_credentials&client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(TOKEN_URI, HttpMethod.POST, requestEntity, String.class);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
        return objectMapper.readTree(response.getBody()).get("access_token").asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("에러발생!!", e);
        }
    }
}
