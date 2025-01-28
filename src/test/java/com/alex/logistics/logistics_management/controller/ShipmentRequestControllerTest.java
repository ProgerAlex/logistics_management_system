package com.alex.logistics.logistics_management.controller;

import com.alex.logistics.logistics_management.model.ShipmentRequest;
import com.alex.logistics.logistics_management.model.User;
import com.alex.logistics.logistics_management.service.ShipmentRequestService;
import com.alex.logistics.logistics_management.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@WebMvcTest(ShipmentRequestController.class)
@Import({NetworkConfigTest.class, ShipmentRequestControllerTest.TestConfig.class})
class ShipmentRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShipmentRequestService shipmentRequestService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void testGetAllRequests() throws Exception {
        // Arrange
        when(shipmentRequestService.getAllRequests()).thenReturn(List.of());

        // Act
        // Assert
        mockMvc.perform(get("/api/shipments"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of())));
    }

    @Test
    public void testCreateRequest() throws Exception {
        // Arrange
        ShipmentRequest inputRequest = new ShipmentRequest();
        inputRequest.setClientName("Alice");
        inputRequest.setCargoDescription("Books");


        ShipmentRequest expectedAnswer = new ShipmentRequest();
        expectedAnswer.setId(1L);
        expectedAnswer.setClientName("Alice");
        expectedAnswer.setCargoDescription("Books");
        expectedAnswer.setStatus("Pending");

        when(shipmentRequestService.getAllRequests()).thenReturn(List.of());
        when(shipmentRequestService.createRequest(inputRequest)).thenReturn(expectedAnswer);

        //Act
        //Assert
        mockMvc.perform(get("/api/shipments"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of())));

        mockMvc.perform(post("/api/shipments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedAnswer)));

    }


    @TestConfiguration
    static class TestConfig {
        @Bean
        public ShipmentRequestService shipmentRequestService() {
            return mock(ShipmentRequestService.class);
        }

    }
}
