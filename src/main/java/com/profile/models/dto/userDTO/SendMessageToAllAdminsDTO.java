package com.profile.models.dto.userDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SendMessageToAllAdminsDTO {

    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String subject;
    @Size(min = 1)
    private String message;
}
