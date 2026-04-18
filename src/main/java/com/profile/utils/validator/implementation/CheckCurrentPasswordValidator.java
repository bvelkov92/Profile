package com.profile.utils.validator.implementation;

import com.profile.models.entity.User;
import com.profile.service.serviceAnotation.UserService;
import com.profile.utils.validator.anotation.CheckCurrentPasswordValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CheckCurrentPasswordValidator implements ConstraintValidator<CheckCurrentPasswordValidation, String> {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public CheckCurrentPasswordValidator(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    public void initialize(CheckCurrentPasswordValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String oldPassword, ConstraintValidatorContext context) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User foundUser = userService.getUserByUsername(username);

        if (!passwordEncoder.matches(oldPassword, foundUser.getPassword())){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Wrong password")
            .addConstraintViolation();
            return false;
        }
        return true;
    }
}
