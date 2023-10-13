package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.services.UserServices;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class Initializer implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserServices userServices;

    @Autowired
    public Initializer(RoleRepository roleRepository, PasswordEncoder passwordEncoder,
                       UserServices userServices) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userServices = userServices;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        Role guestRole = new Role();
        guestRole.setRole("GUEST");
        roleRepository.save(guestRole);

        Role userRole = new Role();
        userRole.setRole("USER");
        roleRepository.save(userRole);

        Role adminRole = new Role();
        adminRole.setRole("ADMIN");
        roleRepository.save(adminRole);

        Set<Role> guestRoles = new LinkedHashSet<>();
        Collections.addAll(guestRoles, guestRole);

        Set<Role> userRoles = new LinkedHashSet<>();
        Collections.addAll(userRoles, userRole, guestRole);

        Set<Role> adminRoles = new LinkedHashSet<>();
        Collections.addAll(adminRoles, adminRole, userRole, guestRole);

        User guest = new User("guest", "guest", 20, "guest@mail.ru",
                passwordEncoder.encode("guest"), guestRoles);
        userServices.createUser(guest);
        System.out.println("создана запись GUEST: login - " + guest.getEmail() + ", password - guest.");

        User user = new User("user", "User", 30, "user@mail.ru",
                passwordEncoder.encode("user"), userRoles);
        userServices.createUser(user);
        System.out.println("создана запись USER: login - " + user.getEmail() + ", password - user.");

        User admin = new User("admin", "admin", 40, "admin@mail.ru",
                passwordEncoder.encode("admin"), adminRoles);
        userServices.createUser(admin);
        System.out.println("создана запись ADMIN: login - " + admin.getEmail() + ", password - admin.");
    }
}
