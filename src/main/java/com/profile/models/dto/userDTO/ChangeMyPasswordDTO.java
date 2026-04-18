package com.profile.models.dto.userDTO;

import com.profile.utils.validator.anotation.CheckCurrentPasswordValidation;
import com.profile.utils.validator.anotation.CheckNewAndConfirmPasswordsValidation;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@CheckNewAndConfirmPasswordsValidation
public class ChangeMyPasswordDTO {

    @CheckCurrentPasswordValidation
    private String oldPassword;

    @Size(min = 6, message = "Password must be at least 6 characters")
    private String newPassword;
    private String confirmPassword;
}
