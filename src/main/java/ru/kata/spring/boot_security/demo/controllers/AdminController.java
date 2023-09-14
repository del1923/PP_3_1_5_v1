package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleServices;
import ru.kata.spring.boot_security.demo.services.UserServices;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserServices userServices;
    private final RoleServices roleServices;

    @Autowired
    public AdminController(UserServices userServices, RoleServices roleServices) {
        this.userServices = userServices;
        this.roleServices = roleServices;
    }

    @GetMapping()
    public String allUsers(Model model, Principal principal) {
        model.addAttribute("showAllUsers", userServices.getAllUsers());
        model.addAttribute("user", userServices.findByUsername(principal.getName()));
        model.addAttribute( "userName", principal.getName());
        return "admin";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userServices.showUser(id));
        return "showUser";
    }

    @GetMapping("/showUser")
    public String showUser(Model model, Principal principal) {
        model.addAttribute("user", userServices.findByUsername(principal.getName()));
        return "showUser";
    }

    @GetMapping("/newUser")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("userRoles", roleServices.getAllRoles());
        return "/newUser";
    }

    @PostMapping
    public String save(@ModelAttribute("user") User user) {
        userServices.saveUser(user);
        return "redirect:/admin/";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userServices.showUser(id));
        model.addAttribute("userRoles", roleServices.getAllRoles());
        return "/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User userUpdate, @PathVariable("id") Long id) {
        userServices.updateUser(userUpdate, id);
        return "redirect:/admin/";
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userServices.deleteUserById(id);
        return "redirect:/admin/";
    }
}
