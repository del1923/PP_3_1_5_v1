package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.Set;

public interface UserServices {
    User findByUsername(String name);

    void saveUser(User user);

    User showUser(Long id);

    void deleteUserById(Long id);

    Set<User> getAllUsers();

    void updateUser(User user, Long id);

    User findUserById(Long id);
    void createUser( User user );
}
