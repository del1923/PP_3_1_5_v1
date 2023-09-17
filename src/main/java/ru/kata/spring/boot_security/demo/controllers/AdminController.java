package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleServices;
import ru.kata.spring.boot_security.demo.services.UserServices;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserServices userServices;
    private final RoleServices roleServices;
    private final UserValidator userValidator;

    @Autowired
    public AdminController(UserServices userServices, RoleServices roleServices, UserValidator userValidator) {
        this.userServices = userServices;
        this.roleServices = roleServices;
        this.userValidator = userValidator;
    }

    @GetMapping()
    public String allUsers(Model model, Principal principal) {
        model.addAttribute("showAllUsers", userServices.getAllUsers());
        model.addAttribute("user", userServices.findByEmail(principal.getName()));
        model.addAttribute( "userName", principal.getName());
        model.addAttribute("roles", roleServices.getAllRoles());
        return "admin";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userServices.showUser(id));
        return "showUser";
    }

    @GetMapping("/showUser")
    public String showUser(Model model, Principal principal) {
        model.addAttribute("user", userServices.findByEmail(principal.getName()));
        return "showUser";
    }

    @GetMapping("/newUser")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("userRoles", roleServices.getAllRoles());
        return "/new";
    }
//    @PostMapping()
//    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
//        userValidator.validate(user, bindingResult);
//        if (bindingResult.hasErrors()) {
//            return "/new";
//        }
//        userServices.saveUser(user);
//        return "redirect:admin";
//    }

    @PostMapping("/save")
    public String save(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/new";
        }
        userServices.saveUser(user);
        return "redirect:/admin/";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userServices.showUser(id));
        model.addAttribute("userRoles", roleServices.getAllRoles());
        return "/edit";
    }

    @PatchMapping("/edit/{id}")
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
