package com.profile.service.serviceImpl;

import com.profile.models.entity.Project;
import com.profile.models.entity.User;
import com.profile.repository.ProjectRepository;
import com.profile.repository.UserRepository;
import org.aspectj.weaver.patterns.PerObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceImplTest {

    @Mock
    private ProjectServiceImpl mockProjectService;

    @Mock
    private ProjectRepository mockProjectRepository;

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private ModelMapper mockModelMapper;

    @BeforeEach
    void setUp(){
        mockProjectService = new ProjectServiceImpl(
                mockProjectRepository,
                mockUserRepository,
                mockModelMapper
        );
    }

    @Test
    void getMyAllProjects() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        User creator = new User();
        creator.setUsername("regUser");

        Project project = new Project();
        project.setProjectCreator(creator);
        project.setProjectName("myProjectOneForTest");

        Project project2 = new Project();
        project2.setProjectCreator(creator);
        project2.setProjectName("myProjectOneForTest2");

        List <Project> allProjects = new ArrayList<>();
        allProjects.add(project);
        allProjects.add(project2);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("regUser");
        when(mockProjectRepository.findAllByProjectCreator_Username(creator.getUsername())).thenReturn(allProjects);

        List<Project> getAllMyProjects = mockProjectService.getMyAllProjects();

        Assertions.assertEquals(2, getAllMyProjects.size());
        Assertions.assertEquals("myProjectOneForTest", getAllMyProjects.getFirst().getProjectName());

    }

    @Test
    void addProject() {
    }
}
