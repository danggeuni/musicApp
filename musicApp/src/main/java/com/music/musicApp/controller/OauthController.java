package com.music.musicApp.controller;


import com.music.musicApp.controller.dto.oauth.OauthToken;
import com.music.musicApp.controller.dto.oauth.OauthUser;
import com.music.musicApp.controller.dto.user.AddUserRequest;
import com.music.musicApp.service.OauthService;
import com.music.musicApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class OauthController {
    private final OauthService oauthService;
    private final UserService userService;
    private final HttpSession session;

    @Autowired
    public OauthController(OauthService oauthService, UserService userService, HttpSession session) {
        this.oauthService = oauthService;
        this.userService = userService;
        this.session = session;
    }

    @GetMapping("/auth/kakao/callback{code}")
    public String kakaoCallback(Model model, @RequestParam String code) {
        OauthToken data = oauthService.getToken(code);
        OauthUser user = oauthService.getData(data);

        String randomPwd = UUID.randomUUID().toString();

        AddUserRequest addUserRequest = new AddUserRequest();
        addUserRequest.setUserId(user.getId());
        addUserRequest.setName(user.getProperties().getNickname());
        addUserRequest.setPassword(randomPwd);

        if (userService.findById(addUserRequest.getUserId()) == null) {
            userService.joinUser(addUserRequest, addUserRequest.getPassword());
        }

        session.setAttribute("userId", addUserRequest.getUserId());
        return "redirect:/music/main";
    }
}
