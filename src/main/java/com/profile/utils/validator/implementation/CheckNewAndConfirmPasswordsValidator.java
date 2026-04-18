package com.profile.utils.validator.implementation;
import com.profile.models.dto.userDTO.ChangeMyPasswordDTO;
import com.profile.utils.validator.anotation.CheckNewAndConfirmPasswordsValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class CheckNewAndConfirmPasswordsValidator implements ConstraintValidator<CheckNewAndConfirmPasswordsValidation, ChangeMyPasswordDTO> {


    @Override
    public boolean isValid(ChangeMyPasswordDTO changeMyPasswordDTO, ConstraintValidatorContext context) {

        if (!changeMyPasswordDTO.getNewPassword().equals(changeMyPasswordDTO.getConfirmPassword())){

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Passwords aren't equals!")
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
