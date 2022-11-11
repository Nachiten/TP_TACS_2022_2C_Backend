package com.tacs.backend;

import com.tacs.backend.dto.creation.PlayerCreationDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("BackendTests")
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.MethodName.class)
public class BackendApplicationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void _1_should_return_healthy() throws Exception {
        this.mockMvc.perform(get("/health")).andDo(print()).andExpect(status().isOk())
            .andExpect(content().string(containsString("Healthy!")));
    }

    @Test
    public void _2_should_return_404() throws Exception {
        this.mockMvc.perform(get("/non-existent")).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void _3_should_return_invalid_query_param() throws Exception {
        this.mockMvc.perform(get("/statistics/matches").queryParam("hours", "abc"))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errorCode").value("INVALID_QUERY_PARAM"));
    }

    @Test
    public void _4_should_create_match() throws Exception {
        String matchCreation = "{\"startingDateTime\": \"2023-09-17T21:18\",\"location\": \"abc\"}";

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/matches")
                .content(matchCreation)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
    }

    @Test
    public void _5_should_return_invalid_email() throws Exception {
        PlayerCreationDTO playerCreation = new PlayerCreationDTO(1234L, "jorgeabc");

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/matches/1/players")
                .content(asJsonString(playerCreation))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errorCode").value("INVALID_BODY"))
            .andExpect(jsonPath("$.message").value("email: must be a well-formed email address"));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /* TODO - Add tests:
        - Create valid player
        - Create invalid player
        - Get valid statistics
     */
}
