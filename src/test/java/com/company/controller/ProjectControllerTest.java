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
        token = "Bearer " + "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJpVmRPVXZvcVM2Q2ZKZUpCY2JXNElaNU1sbTJiLUI5RXhJakpsa21aSGZZIn0.eyJleHAiOjE2OTEwNjIyMTUsImlhdCI6MTY5MTA2MTkxNSwianRpIjoiM2E2ZThiZmMtYmI5ZS00NDlhLTk2NGYtNjlmYjk4ODBmOWU2IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2F1dGgvcmVhbG1zL2NvbXBhbnktZGV2IiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjJhZWQ2YjgxLWU2MzAtNDdlOS1iNjQ3LTgwM2U5NDI0ZWM1YSIsInR5cCI6IkJlYXJlciIsImF6cCI6InRpY2tldGluZy1hcHAiLCJzZXNzaW9uX3N0YXRlIjoiOGY4ZGZiNWItYzVkZi00YzJkLWE2MzctMWI3NDA5Yzg4MjczIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjgwODEiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtY29tcGFueS1kZXYiLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsidGlja2V0aW5nLWFwcCI6eyJyb2xlcyI6WyJFbXBsb3llZSJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJvcGVuaWQgZW1haWwgcHJvZmlsZSIsInNpZCI6IjhmOGRmYjViLWM1ZGYtNGMyZC1hNjM3LTFiNzQwOWM4ODI3MyIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJ0b20ifQ.YnMmT3iZG4Wjx_Ty1DZg9POrWMeghVuckMDpzBkEpZ-U4Yx4XDpTzigSngtmBM65Rt47RdUbo0sUJXZJkJoxCXPpdvMszh_FCIkj9kbMBI8hE5joCb_MBeFqMXu51r10aKrD-0UffsG1chLCc0wzGpZQsOi6eVL9M6TCL1ruQXeNbkWbLwQl9rzGhbRwfr6h31eQ_cDJSJYB1qov3oFPlF8wAgyT5bmDjPM9PirCcLYjVghaTOZNpfNrRCzlX1HMG5Z2RzCmzDwOTE6K0Smhx64NL6nAlH3YsIidC3sSkpf3_mebHb779DP1THi8FbPaKLfNGCjrgOhWj2MTo4WDtQ";
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
                .andExpect(status().isOk());

    }


}