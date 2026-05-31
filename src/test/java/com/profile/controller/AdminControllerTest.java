package com.profile.controller;
import com.profile.models.entity.User;
import com.profile.models.enums.RolesEnum;
import com.profile.service.serviceAnotation.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(roles = "ADMIN")
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService mockUserService;

    @Test
    void getAdminPanelPage() throws Exception {

        when(mockUserService.getAllUsers())
                .thenReturn(List.of());

        mockMvc.perform(get("/admin/panel"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-panel"))
                .andExpect(model().attributeExists("allUsers"))
                .andExpect(model().attributeExists("functionsDTO"));
    }

    @Test
    void getAllUsersPage() throws Exception {

        when(mockUserService.getAllUsers())
                .thenReturn(List.of());

        mockMvc.perform(get("/admin/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("all-registered-users"))
                .andExpect(model().attributeExists("allUsers"));
    }

    @Test
    void postChangeRolePageSuccess() throws Exception {

        User user = new User();

        ReflectionTestUtils.setField(user, "id", 2L);
        user.setRole(RolesEnum.USER);

        when(mockUserService.getUserByUsername("pesho"))
                .thenReturn(user);

        mockMvc.perform(post("/admin/panel/submit")
                        .param("username", "pesho")
                        .param("functionName", "Change role")
                        .param("role", RolesEnum.ADMIN.name()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/panel"));

        verify(mockUserService).executeAdminAction(any());
    }


    @Test
    void postChangeRolePageValidationError() throws Exception {

        User user = new User();

        ReflectionTestUtils.setField(user, "id", 1L);
        user.setRole(RolesEnum.ADMIN);

        when(mockUserService.getUserByUsername("admin"))
                .thenReturn(user);

        mockMvc.perform(post("/admin/panel/submit")
                        .param("username", "admin")
                        .param("functionName", "Change role")
                        .param("role", RolesEnum.USER.name()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/panel"));
    }
}