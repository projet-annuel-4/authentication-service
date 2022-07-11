package fr.esgi.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.esgi.app.dto.user.UserRequest;
import fr.esgi.app.security.JwtAuthenticationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static fr.esgi.app.util.TestConstants.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/create-user-before.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/create-user-after.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @WithUserDetails(USER_EMAIL)
    public void getUserInfo() throws Exception {
        mockMvc.perform(get(URL_USERS_BASIC + "/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.firstName").value(FIRST_NAME))
                .andExpect(jsonPath("$.email").value(USER_EMAIL))
                .andExpect(jsonPath("$.roles").value(ROLE_USER));
    }

    @Test
    public void getUserInfoByJwt() throws Exception {
        mockMvc.perform(get(URL_USERS_BASIC + "/info").header("Authorization", JWT_TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.email").value(ADMIN_EMAIL))
                .andExpect(jsonPath("$.roles").value(ROLE_ADMIN));
    }

    @Test(expected = JwtAuthenticationException.class)
    public void getUserInfoByJwtExpired() throws Exception {
        mockMvc.perform(get(URL_USERS_BASIC + "/info").header("Authorization", "jwt"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    public void updateUserInfo() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setFirstName(USER2_NAME);
        userRequest.setLastName(USER2_NAME);

        mockMvc.perform(put(URL_USERS_BASIC + "/edit")
                .content(mapper.writeValueAsString(userRequest))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.email").value(USER_EMAIL))
                .andExpect(jsonPath("$.firstName").value(USER2_NAME))
                .andExpect(jsonPath("$.lastName").value(USER2_NAME));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    public void updateUserInfo_ShouldInputFieldsAreEmpty() throws Exception {
        UserRequest userRequest = new UserRequest();

        mockMvc.perform(put(URL_USERS_BASIC + "/edit")
                .content(mapper.writeValueAsString(userRequest))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.firstNameError", is("First name cannot be empty")))
                .andExpect(jsonPath("$.lastNameError", is("Last name cannot be empty")));
    }

}
