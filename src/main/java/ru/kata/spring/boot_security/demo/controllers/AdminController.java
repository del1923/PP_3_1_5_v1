package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleServices;
import ru.kata.spring.boot_security.demo.services.UserServices;


import javax.validation.Valid;
import java.security.Principal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * RestController – это Controller, который управляет REST запросами и ответами.
 * Такие Спринг-приложении, которые принимают http-запросы и не реализуют представления,
 * а отдают сырые данные в формате JSON (в 99% случаев, т.к. это самый распространенный формат),
 * называются REST API.
 * По принятому стандарту url любого запроса в REST API должно начинаться с /api,
 * поэтому всему rest-контроллеру ставим такой url
 * Теперь, когда со стороны клиента, т.е. браузера, будет приходить запрос, содержащий в url "/api",
 * то Спринг с помощью функционала проекта Jackson будет конвертировать данные в JSON-формат
 * и в теле http-response будет передан JSON, который отобразится в браузере.
 * <p>
 * Чтобы получать о запросах и ответа больше инфы, есть разные проги. Одна из них - Postman.
 * Т.е. в качестве клиента будет не браузер, а Postman.
 */


@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserServices userServices;
    private final RoleServices roleServices;


    @Autowired
    public AdminController(UserServices userServices, RoleServices roleServices) {
        this.userServices = userServices;
        this.roleServices = roleServices;
    }

    @GetMapping("/")
    public User getUser(Principal principal) {
        System.out.println( userServices.findByEmail( principal.getName()) );
        return userServices.findByEmail( principal.getName());
    } // получаем пользователя по имени

    @GetMapping("/users")
    public ResponseEntity<List<User>> showAllUsers() {
        List<User> listUsers = new ArrayList<>(userServices.getAllUsers());
        return ResponseEntity.ok(listUsers);
    } //получаем список всех пользователей

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userServices.findUserById( id ));
    } // получаем пользователя по ID

    @PostMapping("/add")
    public void addUser(@RequestBody User user) {
        userServices.saveUser(user);
    }


    @PostMapping()
    public ResponseEntity<HttpStatus> addNewUser(@RequestBody User user) {
        userServices.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/roles")
    public List<Role> roles() {
        return roleServices.getAllRoles();
    }

    @PatchMapping("/update")
    public void updateUser(@RequestBody User user) {
        userServices.saveUser(user);
    }

}





