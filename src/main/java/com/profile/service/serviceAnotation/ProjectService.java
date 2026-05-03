package com.profile.service.serviceAnotation;


import com.profile.models.dto.projectDTO.AddNewProjectDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ProjectService {


    void getMyAllProjects();
    void addProject(AddNewProjectDTO addNewProjectDTO, MultipartFile projectImage);
}
