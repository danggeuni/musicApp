package com.music.musicApp.config.oauth;

import com.music.musicApp.domain.entity.UserEntity;
import com.music.musicApp.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class Oauth2UserCustomService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Autowired
    public Oauth2UserCustomService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        saveOrUpdate(user);
        return user;
    }

//    해당 코드 따로 공부해야 함. map 사용이 불가하여 진행 못함. 276페이지
    private UserEntity saveOrUpdate(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String userId = (String) attributes.get("userId");
        String name = (String) attributes.get("name");
        UserEntity user = userRepository.findByEmail(userId);
        return userRepository.joinUser(user);
    }
}
