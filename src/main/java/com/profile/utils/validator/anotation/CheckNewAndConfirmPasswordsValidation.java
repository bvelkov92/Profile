package com.profile.utils.validator.anotation;

import com.profile.utils.validator.implementation.CheckNewAndConfirmPasswordsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = CheckNewAndConfirmPasswordsValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckNewAndConfirmPasswordsValidation {


    String message() default "Passwords miss match!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
