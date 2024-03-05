package com.music.musicApp.service;

import com.music.musicApp.controller.dto.user.AddUserRequest;
import com.music.musicApp.controller.dto.user.LoginUserRequest;
import com.music.musicApp.domain.entity.UserEntity;
import com.music.musicApp.domain.repository.UserRepository;
import com.music.musicApp.utils.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final HttpSession session;

    @Autowired
    public UserService(UserRepository userRepository, HttpSession session, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.session = session;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

//    추가 작업 필요
    public void joinUser(AddUserRequest request, String checkPwd) {
        UserEntity checkId = userRepository.findByEmail(request.getEmail());

        if(checkId != null) {
            throw new RuntimeException("이미 가입된 아이디염");
        }

        String originPwd = request.getPassword();
        if(!originPwd.equals(checkPwd)) {
            throw new RuntimeException("비밀번호가 서로다름");
        }

        // 암호화 및 entity 생성 (기존 코드)
        // Encryption encryption = new Encryption();
        // String encryptPwd = encryption.getEncrypt(request.getPassword(), encryption.salt);

        // 암호화 및 entity 생성 (변경 코드 (시큐리티 사용))
        String encryptPwd = bCryptPasswordEncoder.encode(request.getPassword());
        UserEntity updateDate = new UserEntity(null, request.getEmail(), request.getName(), encryptPwd);

        userRepository.joinUser(updateDate);
    }

    public void loginUser(LoginUserRequest request) {
        UserEntity entity = userRepository.findByEmail(request.getEmail());

        if(entity == null) {
            throw new RuntimeException("가입된 정보가 없습니다.");
        }

        // 기존 코드
        // Encryption encryption = new Encryption();
        // String encryptPwd = encryption.getEncrypt(request.getPassword(), encryption.salt);

        // 변경 코드
        String encryptPwd = bCryptPasswordEncoder.encode(request.getPassword());

        if(!encryptPwd.equals(entity.getPassword())) {
            throw new RuntimeException("비밀번호가 다릅니다.");
        }
    }

    public UserEntity findByEmail(String userId) {
        return userRepository.findByEmail(userId);
    }


    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository.findByEmail(email);
    }
}
