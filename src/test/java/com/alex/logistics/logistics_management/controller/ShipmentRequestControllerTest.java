package com.alex.logistics.logistics_management.controller;

import com.alex.logistics.logistics_management.TestConfig;
import com.alex.logistics.logistics_management.model.ShipmentRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Import({TestConfig.class})
class ShipmentRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void shouldReturnEmptyShipmentList() throws Exception {
        mockMvc.perform(get("/api/shipments"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void testCreateRequest() throws Exception {

        // Watch the list of GET request is null
        mockMvc.perform(get("/api/shipments"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));



        ShipmentRequest expectedAnswer = new ShipmentRequest();
        expectedAnswer.setId(1L);
        expectedAnswer.setClientName("Alice");
        expectedAnswer.setCargoDescription("Books");
        expectedAnswer.setStatus("Pending");

        // Perform the POST request
        mockMvc.perform(post("/api/shipments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"clientName\":\"Alice\",\"cargoDescription\":\"Books\",\"status\":\"Pending\"}")) // Convert inputRequest to JSON
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedAnswer))); // Compare response with expected JSON



        // Watch the list of GET request is not null and contains the item
        mockMvc.perform(get("/api/shipments"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"clientName\":\"Alice\",\"cargoDescription\":\"Books\",\"status\":\"Pending\"}]"));
    }
}
