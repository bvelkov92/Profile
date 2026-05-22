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

        Assertions.assertEquals(2, getAllMyProjects.size());
        Assertions.assertEquals("myProjectOneForTest", getAllMyProjects.getFirst().getProjectName());
        Assertions.assertEquals("regUser", getAllMyProjects.getLast().getProjectCreator().getUsername());
    }

    @Test
    void addProject() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        AddNewProjectDTO projectWithImage = new AddNewProjectDTO();
        projectWithImage.setProjectName("The most new project with image");

        MockMultipartFile mockFile = new MockMultipartFile(
                "projectImage",          // name
                "image.png",             // original filename
                "image/png",             // content type
                "fake image content".getBytes()
        );
        //TODO: Да се провери, защо не минава.
//
//        AddNewProjectDTO projectNoImage = new AddNewProjectDTO();
//        projectNoImage.setProjectName("The most new project with image");

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("regUser");
        when(mockUserRepository.findByUsername(authentication.getName())).thenReturn(Optional.of(creator));
        mockProjectService.addProject(projectWithImage,mockFile);

        List<Project> myAllProjects = mockProjectService.getMyAllProjects();


        Assertions.assertEquals(3, myAllProjects.size());


    }
}
