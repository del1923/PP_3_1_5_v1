package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.services.UserServices;


@Controller
public class LoginController {

    private final UserServices userServices;
//    private final PasswordEncoder passwordEncoder;


    @Autowired
    public LoginController(UserServices userServices) {
        this.userServices = userServices;
//        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    String login() {
        return "login";
    }
}

