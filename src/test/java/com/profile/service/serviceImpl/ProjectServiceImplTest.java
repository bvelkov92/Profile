package com.profile.service.serviceImpl;

import com.profile.repository.ProjectRepository;
import com.profile.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

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

    }

    @Test
    void addProject() {
    }
}
