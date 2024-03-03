package com.music.musicApp.controller;

import com.music.musicApp.controller.dto.user.AddUserRequest;
import com.music.musicApp.controller.dto.user.LoginUserRequest;
import com.music.musicApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final HttpSession session;

    @Autowired
    public UserController(UserService userService, HttpSession session) {
        this.userService = userService;
        this.session = session;
    }

    @GetMapping("/register")
    public String joinUser(Model model, String checkPwd) {
        model.addAttribute("data", new AddUserRequest());
        model.addAttribute("checkPwd", checkPwd);

        return "user/register";
    }

    @PostMapping("/register")
    public String addUser(@ModelAttribute AddUserRequest request, String checkPwd) {
        userService.joinUser(request, checkPwd);

        return "redirect:/user/login";
    }

    @GetMapping("/login")
    public String loginUser(Model model) {
        model.addAttribute("data", new LoginUserRequest());
        return "user/login";
    }

    @PostMapping("/login")
    public String checkLogin(@ModelAttribute LoginUserRequest request) {
        userService.loginUser(request);
        session.setAttribute("userId", request.getUserId());

        return "redirect:/music/main";
    }

    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/music/main";
    }

}
