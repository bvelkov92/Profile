package com.profile.utils.validator.anotation;


import com.profile.utils.validator.implementation.CheckCurrentPasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CheckCurrentPasswordValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckCurrentPasswordValidation {

    String message() default "Wrong password!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
