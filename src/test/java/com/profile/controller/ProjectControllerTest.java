package com.profile.controller;

import com.profile.service.serviceAnotation.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectController.class)
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService mockProjectService;

    @Test
    void getPortfolioPage() throws Exception {

        mockMvc.perform(get("/myprojects"))
                .andExpect(status().isOk())
                .andExpect(view().name("project"))
                .andExpect(model().attributeExists("projects"));
    }

    @Test
    void getAddNewProject() throws Exception {

        mockMvc.perform(get("/myprojects/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-new-project"))
                .andExpect(model().attributeExists("addNewProjectDTO"));
    }
    @Test
    void postAddNewProjectSuccess() throws Exception {

        MockMultipartFile file =
                new MockMultipartFile(
                        "projectImage",
                        "test.jpg",
                        "image/jpeg",
                        "test".getBytes());

        mockMvc.perform(multipart("/myprojects/new")
                        .file(file)
                        .param("projectName", "Test Project")
                        .param("projectDescription", "Test Description")
                        .param("projectGitHubLink", "https://github.com/test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/myprojects"));

        verify(mockProjectService)
                .addProject(any(), any());
    }

    @Test
    void postAddNewProjectValidationError() throws Exception {

        MockMultipartFile file =
                new MockMultipartFile(
                        "projectImage",
                        "",
                        "image/jpeg",
                        new byte[0]);

        mockMvc.perform(multipart("/myprojects/new")
                        .file(file))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/myprojects/new"));
    }
}