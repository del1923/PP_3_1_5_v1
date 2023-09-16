package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.services.UserServices;

import java.security.Principal;

@Controller
public class UserController {

    private final UserServices userServices;

    @Autowired
    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }


    @GetMapping("/user")
    public String showUser(Model model, Principal principal) {
        model.addAttribute("user", userServices.findByEmail(principal.getName()));
        return "/showUser";
    }


}
