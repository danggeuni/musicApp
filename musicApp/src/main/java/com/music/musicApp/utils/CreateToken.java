package com.music.musicApp.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class CreateToken {
    private static final String CLIENT_ID = "bf42d193c4d6484e9a1fd979ca8d78e2";
    private static final String CLIENT_SECRET = "081f145afdaf46ecb159b01694cb0e67";
    private final static String TOKEN_URI = "https://accounts.spotify.com/api/token";

    public String getAccessToken() {
        try {
            HttpPost post = new HttpPost(TOKEN_URI);
            post.setHeader("Content-Type", "application/x-www-form-urlencoded");

            String encodedClientId = URLEncoder.encode(CLIENT_ID, StandardCharsets.UTF_8);
            String encodedClientSecret = URLEncoder.encode(CLIENT_SECRET, StandardCharsets.UTF_8);
            StringEntity entity = new StringEntity("grant_type=client_credentials&client_id=" + encodedClientId + "&client_secret=" + encodedClientSecret);

            post.setEntity(entity);

            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute(post);

            String result = EntityUtils.toString(response.getEntity());

            ObjectMapper mapper = new ObjectMapper();

            return mapper.readTree(result).get("access_token").asText();

        } catch (IOException e) {
            throw new RuntimeException("API 통신에 실패했습니다.");
        }
    }
}
