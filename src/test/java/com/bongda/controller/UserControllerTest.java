package com.bongda.controller;

import com.bongda.model.User;
import com.bongda.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser(username = "admin", roles = "ADMIN")
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    private User createMockUser() {
        User user = new User();
        user.setAccountId(1L);
        user.setUsername("testuser");
        user.setPassword("password");
        user.setFullName("Test User");
        user.setStatus("ACTIVE");
        user.setAccountType("USER");
        user.setEmail("test@example.com");
        user.setDiachi("123 Street");
        user.setTimelog(0);
        user.setCreatedDate(LocalDateTime.now());
        return user;
    }

    @Test
    void testRegisterUser() throws Exception {
        User mockUser = createMockUser();
        Mockito.when(userService.registerUser("testuser", "password", "Test User", "test@example.com")).thenReturn(mockUser);

        mockMvc.perform(post("/shopbongda/auth/register")
                .param("username", "testuser")
                .param("password", "password")
                .param("fullName", "Test User")
                .param("email", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    void testLoginUser() throws Exception {
        User mockUser = createMockUser();
        Mockito.when(userService.loginUser("testuser", "password")).thenReturn(mockUser);

        mockMvc.perform(post("/shopbongda/auth/login")
                .param("username", "testuser")
                .param("password", "password"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Test User"));
    }

    @Test
    void testGetUserByUsername() throws Exception {
        User mockUser = createMockUser();
        Mockito.when(userService.getUserByUsername("testuser")).thenReturn(mockUser);

        mockMvc.perform(get("/shopbongda/admin/user/testuser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void testGetAllUsers() throws Exception {
        User mockUser = createMockUser();
        Mockito.when(userService.getAllUsers()).thenReturn(List.of(mockUser));

        mockMvc.perform(get("/shopbongda/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("testuser"));
    }

    @Test
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/shopbongda/admin/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully"));
    }

    @Test
    void testUpdateUser() throws Exception {
        User mockUser = createMockUser();
        Mockito.when(userService.updateUser(Mockito.eq(1L),
                Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(mockUser);

        mockMvc.perform(put("/shopbongda/admin/update/1")
                .param("fullName", "Updated Name")
                .param("status", "ACTIVE")
                .param("username", "testuser")
                .param("accountType", "USER")
                .param("email", "test@example.com")
                .param("diachi", "456 New St")
                .param("matkhau", "newpass"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Test User"));
    }
}
