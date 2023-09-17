package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.UserServices;

import java.util.Optional;


@Component
public class UserValidator implements Validator {

    private final UserServices userServices;

    @Autowired
    public UserValidator(UserServices userServices) {
        this.userServices = userServices;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target; //даункастим к Юзеру
        Optional<User> userOptional = Optional.ofNullable(userServices.findByEmail(user.getEmail()));
        if (userOptional.isPresent()) {
            errors.rejectValue("email", "", "e-mail уже используется");
        }
    }
}
