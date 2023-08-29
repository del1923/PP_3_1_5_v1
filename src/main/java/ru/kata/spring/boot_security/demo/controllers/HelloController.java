package ru.kata.spring.boot_security.demo.controllers;


import com.mysql.cj.protocol.AuthenticationProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/userInfo")
    public String userInfo() { return "userInfo";}


}