package com.profile.service.serviceAnotation;


import com.profile.models.dto.projectDTO.AddNewProjectDTO;
import com.profile.models.entity.Project;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProjectService {


    List<Project> getMyAllProjects();
    void addProject(AddNewProjectDTO addNewProjectDTO, MultipartFile projectImage);
}
