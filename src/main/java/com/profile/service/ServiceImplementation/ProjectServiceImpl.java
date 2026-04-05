package com.profile.service.ServiceImplementation;

import com.profile.models.entity.Project;
import com.profile.repository.ProjectRepository;
import com.profile.repository.UserRepository;
import com.profile.service.serviceAnotation.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Project> getMyAllProjects(Long id) {
        return this.projectRepository.findAllByProjectCreator_Id(id);
    }
}
