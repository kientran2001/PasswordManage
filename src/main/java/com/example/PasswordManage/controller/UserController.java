package com.example.PasswordManage.controller;

import com.example.PasswordManage.model.User;
import com.example.PasswordManage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/login")
    public String login() {
        return "index";
    }

    @GetMapping("/register")
    public String register(ModelMap modelMap) {
        modelMap.addAttribute("user", new User());
        return "register";
    }

//    @PostMapping("/register")
//    public String register(ModelMap modelMap, User user) {
//        try {
//
//        } catch (Exception e) {
//
//        }
//    }
}
