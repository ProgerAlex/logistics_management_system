package com.alex.logistics.logistics_management.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("default")
@AutoConfigureMockMvc
class SecurityConfigIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldAllowAccessToShipmentsEndpointWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/api/shipments"))
                .andExpect(status().isOk()); // Проверяем, что доступ разрешен без авторизации
    }

    @Test
    void shouldDenyAccessToOtherEndpointsWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/api/null"))
                .andExpect(status().isForbidden()); // Проверяем, что доступ к защищённым ресурсам запрещен
    }
}