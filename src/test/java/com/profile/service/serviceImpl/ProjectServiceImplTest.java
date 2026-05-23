package com.profile.service.serviceImpl;

import com.profile.models.dto.projectDTO.AddNewProjectDTO;
import com.profile.models.entity.Project;
import com.profile.models.entity.User;
import com.profile.repository.ProjectRepository;
import com.profile.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

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

    Project project = new Project();
    Project project2 = new Project();
    User creator = new User();

    @BeforeEach
    void setUp(){
        mockProjectService = new ProjectServiceImpl(
                mockProjectRepository,
                mockUserRepository,
                mockModelMapper
        );
        creator.setUsername("regUser");

        project.setProjectName("myProjectOneForTest");
        project.setProjectCreator(creator);

        project2.setProjectName("myProjectOneForTest2");
        project2.setProjectCreator(creator);
    }

    @Test
    void getMyAllProjects() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        List <Project> allProjects = new ArrayList<>();
        allProjects.add(project);
        allProjects.add(project2);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("regUser");
        when(mockProjectRepository.findAllByProjectCreator_Username(creator.getUsername())).thenReturn(allProjects);

        List<Project> getAllMyProjects = mockProjectService.getMyAllProjects();

        assertEquals(2, getAllMyProjects.size());
        assertEquals("myProjectOneForTest", getAllMyProjects.getFirst().getProjectName());
        assertEquals("regUser", getAllMyProjects.getLast().getProjectCreator().getUsername());
    }

    @Test
    void addProject() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        User creator = new User();
        creator.setUsername("regUser");

        AddNewProjectDTO projectDto = new AddNewProjectDTO();
        projectDto.setProjectName("The most new project with image");

        Project mappedProject = new Project();

        MockMultipartFile mockFile = new MockMultipartFile(
                "projectImage",
                "image.png",
                "image/png",
                "fake image content".getBytes()
        );

        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("regUser");

        when(mockUserRepository.findByUsername("regUser"))
                .thenReturn(Optional.of(creator));

        when(mockModelMapper.map(projectDto, Project.class))
                .thenReturn(mappedProject);

        when(mockProjectRepository.save(any(Project.class)))
                .thenReturn(mappedProject);

        mockProjectService.addProject(projectDto, mockFile);

        verify(mockProjectRepository, times(1)).save(any(Project.class));

        assertEquals(
                "The most new project with image",
                mappedProject.getProjectName()
        );

        assertEquals(
                creator,
                mappedProject.getProjectCreator()
        );

        assertNotNull(mappedProject.getProjectImage());
    }

}

