package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleServices;
import ru.kata.spring.boot_security.demo.services.UserServices;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/rest/admin")
public class AdminController {
    private final UserServices userServices;
    private final RoleServices roleServices;
    //private final UserValidator userValidator;

    @Autowired
    public AdminController(UserServices userServices, RoleServices roleServices, UserValidator userValidator) {
        this.userServices = userServices;
        this.roleServices = roleServices;
        //this.userValidator = userValidator;
    }

    @GetMapping("/allUsers")
    public Set<User> getAllUsers() {
        return userServices.getAllUsers();
    } //получить список всех пользователей

    @GetMapping("/")
    public User admin(Principal principal) {
        return userServices.findByUsername(principal.getName());
    } //получить данные админа по имени

    @GetMapping("/user/{id}")
    public User get(@PathVariable long id) {
        return userServices.findUserById(id);
    } // получить данные юзера по его ID

    @GetMapping("/roles")
    public Set<Role> roles() {
        return roleServices.getAllRoles();
    } // получить список ролей

    @GetMapping("/add")
    public void add(@RequestBody User user) {
        userServices.saveUser(user);
    } // добавить юзера

    @GetMapping("/delete/{id}")
    public void delete(@PathVariable long id) {
        userServices.deleteUserById(id);
    } // удалить юзера

    @GetMapping("/update")
    public void update(@RequestBody User user) {
        userServices.saveUser(user);
    } // редактировать юзера
}


// ниже всё ненужно!
/*
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

    @PostMapping("/save")
    public String save(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/new";
        }
        userServices.saveUser(user);
        return "redirect:/admin/";
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
*/
