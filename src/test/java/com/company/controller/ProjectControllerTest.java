package com.company.controller;

import com.company.dto.ProjectDTO;
import com.company.dto.RoleDTO;
//import com.company.dto.TestResponseDTO;
import com.company.dto.UserDTO;
import com.company.enums.Gender;
import com.company.enums.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {

    @Autowired
    private MockMvc mvc;

    static String token;
    static UserDTO manager;
    static ProjectDTO project;

    @BeforeAll
    static void setUp() {
        token = "Bearer " + "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJpVmRPVXZvcVM2Q2ZKZUpCY2JXNElaNU1sbTJiLUI5RXhJakpsa21aSGZZIn0.eyJleHAiOjE2OTExNTE3MjEsImlhdCI6MTY5MTEzMzcyMSwianRpIjoiYzQ3NjZkNDgtODMwYy00Y2ViLWIzNTEtMTMxNzI3ZTcwMGYwIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2F1dGgvcmVhbG1zL2NvbXBhbnktZGV2IiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjlhMDM1YjU5LTIxMGItNGY1Yi04ODJiLTUwMWUxNDNlNGU2YSIsInR5cCI6IkJlYXJlciIsImF6cCI6InRpY2tldGluZy1hcHAiLCJzZXNzaW9uX3N0YXRlIjoiMTFhMGRlYTEtOTNmNi00MmE0LTljM2UtNDliNWVlOGMzNzUzIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjgwODEiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtY29tcGFueS1kZXYiLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsidGlja2V0aW5nLWFwcCI6eyJyb2xlcyI6WyJNYW5hZ2VyIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBlbWFpbCBwcm9maWxlIiwic2lkIjoiMTFhMGRlYTEtOTNmNi00MmE0LTljM2UtNDliNWVlOGMzNzUzIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsInByZWZlcnJlZF91c2VybmFtZSI6InNhbSJ9.K11HBJmFY3MbnjapDTQ2wZwgUQzwdpXFF2NNokonDl6ZY547IyWRYkQP3jDQU41fXd0_h8udUyyw21mWoshaGOicqUW4nll73Nz7Y7HgiLO8qrG5YR24Y-oaeYkzwS-ZHqq-XWwnkq15QiFQMMqJf6xHe7OqB4H3ORJNvpP3Aevg2nQMaq6tU6WlWyD-RZmHO_Ga4frSFALNQWxEOslNvKwGNWN0ctlHr-zV6tfVR3SX0H6Fr1pRJPC6kZro5AS6IEcy1C_y3aNbtEAPecp2MfOtRFp6S9IdwHplzrYFXzWaOZ_02bgXPYOcrWe6mrV36eWYmijlU7Ic2WLmIaqkHA";
        manager = new UserDTO(2L,
                "",
                "",
                "ozzy",
                "abc1",
                "",
                true,
                "",
                new RoleDTO(2L, "Manager"),
                Gender.MALE);

        project = new ProjectDTO(
                "API Project",
                "PR001",
                manager,
                LocalDate.now(),
                LocalDate.now().plusDays(5),
                "Some details",
                Status.OPEN
        );
    }

    @Test
    void givenNoToken_getProjects() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/project"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void givenToken_getProjects() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/project")
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].projectCode").exists())
                .andExpect(jsonPath("$.data[0].assignedManager.userName").exists())
                .andExpect(jsonPath("$.data[0].assignedManager.userName").isNotEmpty())
                .andExpect(jsonPath("$.data[0].assignedManager.userName").isString())
                .andExpect(jsonPath("$.data[0].assignedManager.userName").value("tom"));

    }

    @Test
    void givenToken_createProject() throws Exception {

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/project")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(toJsonString(project)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("project successfully created"));

    }

    @Test
    void givenToken_updateProject() throws Exception {
        project.setProjectName("API Project-2");
        mvc.perform(MockMvcRequestBuilders.put("/api/v1/project")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(toJsonString(project)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("project successfully updated"));

    }


    private String toJsonString(final Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(obj);
    }


}