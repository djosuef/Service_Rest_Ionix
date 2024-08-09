package com.ionix.demo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.ionix.demo.entity.User;
import com.ionix.demo.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {

        user = User.builder()
            .id(1L)
            .name("Test")
            .username("test")
            .email("correo@test.com")
            .phone("12212")
            .build();

    }

    @Test
    @WithMockUser(username = "user", password = "admin123")
    public void testCreateUser() throws Exception {


        User postUser = User.builder()
            .name("Test")
            .username("test")
            .email("correo@test.com")
            .phone("12212")
            .build();

        Mockito.when(userService.saveUser(postUser)).thenReturn(user);
        mockMvc.perform(post("/api/insert")
            .contentType("application/json")
            .content("{ \"name\": \"Test\", \"username\": \"test\", \"email\": \"correo@test.com\", \"phone\": \"12212\" }"))
            .andExpect(status().isOk())
            .andExpect(content().json("{\"id\":1,\"name\":\"Test\",\"username\":\"test\",\"email\":\"correo@test.com\",\"phone\":\"12212\"}"));
        
    }
}
    