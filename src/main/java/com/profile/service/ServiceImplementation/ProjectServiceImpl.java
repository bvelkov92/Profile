package com.profile.service.ServiceImplementation;

import com.profile.models.dto.projectDTO.AddNewProjectDTO;
import com.profile.models.entity.Project;
import com.profile.models.entity.User;
import com.profile.repository.ProjectRepository;
import com.profile.repository.UserRepository;
import com.profile.service.serviceAnotation.ProjectService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public void getMyAllProjects() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedUser = authentication.getName();
        List<Project> allByProjectCreatorUsername = this.projectRepository.findAllByProjectCreator_Username(loggedUser);
    }

    @Override
    public void addProject(AddNewProjectDTO addNewProjectDTO, MultipartFile projectImage) {
        Project newProject = modelMapper.map(addNewProjectDTO, Project.class);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User projectOwner = this.userRepository.findByUsername(auth.getName()).get();

        if (projectImage != null && !projectImage.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + projectImage.getOriginalFilename();
            Path path = Paths.get("uploads/" + fileName);
            try {
                Files.createDirectories(path.getParent());
                Files.write(path, projectImage.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("File upload failed");
            }
            newProject.setProjectImage(fileName);
            newProject.setProjectCreator(projectOwner);

            this.projectRepository.save(newProject);

        }
    }
}
