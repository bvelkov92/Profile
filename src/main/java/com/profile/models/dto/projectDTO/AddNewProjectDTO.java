package com.profile.models.dto.projectDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddNewProjectDTO {

    private Long projectId;

    @NotBlank
    @Size(min = 2)
    private String projectName;

    private String projectDescription;

    private String projectGitHubLink;

    private String image;

}
