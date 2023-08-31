package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;

public interface UserServices extends UserDetailsService {
    User findByUsername(String name);

    void saveUser(User user);

    User showUser(Long id);

    void deleteUserById(Long id);

    List<User> getAllUsers();

    void updateUser(User user);
}
