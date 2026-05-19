package com.profile.service.serviceImpl;

import com.profile.repository.ProjectRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceImplTest {

    @Mock
    private ProjectServiceImpl mockProjectService;

    @Mock
    private ProjectRepository mockProjectRepository;
}
