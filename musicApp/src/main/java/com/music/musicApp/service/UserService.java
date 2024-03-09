package com.music.musicApp.service;

import com.music.musicApp.controller.dto.user.AddUserRequest;
import com.music.musicApp.controller.dto.user.LoginUserRequest;
import com.music.musicApp.domain.entity.UserEntity;
import com.music.musicApp.domain.repository.UserRepository;
import com.music.musicApp.utils.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final HttpSession session;

    @Autowired
    public UserService(UserRepository userRepository, HttpSession session) {
        this.userRepository = userRepository;
        this.session = session;
    }

    //    추가 작업 필요
    public void joinUser(AddUserRequest request, String checkPwd) {
        UserEntity checkId = userRepository.findById(request.getUserId());

        if (checkId != null) {
            throw new RuntimeException("이미 가입된 아이디염");
        }

        String originPwd = request.getPassword();
        if (!originPwd.equals(checkPwd)) {
            throw new RuntimeException("비밀번호가 서로다름");
        }

        // 암호화 및 entity 생성
        Encryption encryption = new Encryption();
        String encryptPwd = encryption.getEncrypt(request.getPassword(), encryption.salt);
        UserEntity updateDate = new UserEntity(request.getUserId(), request.getName(), encryptPwd, request.getToken());

        userRepository.joinUser(updateDate);
    }

    public void loginUser(LoginUserRequest request) {
        UserEntity entity = userRepository.findById(request.getUserId());

        if (entity == null) {
            throw new RuntimeException("가입된 정보가 없습니다.");
        }

        Encryption encryption = new Encryption();
        String encryptPwd = encryption.getEncrypt(request.getPassword(), encryption.salt);

        if (!encryptPwd.equals(entity.getPassword())) {
            throw new RuntimeException("비밀번호가 다릅니다.");
        }
    }

    public UserEntity findById(String id) {
        return userRepository.findById(id);
    }
}
