package com.alex.logistics.logistics_management.application;

import com.alex.logistics.logistics_management.LogisticsManagementApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
public class LogisticsManagementApplicationTest {

    @Test
    void contextLoads() {
        assertDoesNotThrow(() -> {
            LogisticsManagementApplication.main(new String[]{});
        });
    }
}
