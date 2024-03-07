package com.music.musicApp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.music.musicApp.controller.dto.oauth.OauthToken;
import com.music.musicApp.controller.dto.oauth.OauthUser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class OauthService {

    String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=utf-8";
    String CLIENT_ID = "26207dd989945bf3f623cd138fa235f8";
    String REDIRECT_URI = "http://localhost:6080/auth/kakao/callback";
    String GET_ACCESS_TOKEN_URI = "https://kauth.kakao.com/oauth/token";
    String GET_USER_DATA_ACCESS_URI = "https://kapi.kakao.com/v2/user/me";

    public OauthToken getToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", CONTENT_TYPE);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", CLIENT_ID);
        params.add("redirect_uri", REDIRECT_URI);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> tokenRequest = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                GET_ACCESS_TOKEN_URI,
                HttpMethod.POST,
                tokenRequest,
                String.class);

        ObjectMapper mapper = new ObjectMapper();

        OauthToken data;
        try {
            return data = mapper.readValue(response.getBody(), OauthToken.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("무언가가 잘못되었다!", e);
        }
    }

    public OauthUser getData(OauthToken data) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + data.getAccessToken());
        headers.add("Content-type", CONTENT_TYPE);

        HttpEntity<String> userDataRequest = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(GET_USER_DATA_ACCESS_URI, HttpMethod.GET, userDataRequest, String.class);

        ObjectMapper user = new ObjectMapper();
        try {
            return user.readValue(response.getBody(), OauthUser.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("뭔가 어마어마한 에러가 발생함", e);
        }
    }
}
