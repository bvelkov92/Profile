package com.profile.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "projects")
@Getter
@Setter
public class Project extends BaseEntity {

    @NotBlank
    private String projectName;

    @Column
    private String projectDescription;

    @Column
    private String projectImage;

    @Column
    private String projectGitHubLink;

    @ManyToOne
    @JoinColumn(name = "user_id" )
    private User projectCreator;

}
