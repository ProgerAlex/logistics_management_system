package com.alex.logistics.logistics_management.service;

import com.alex.logistics.logistics_management.model.ShipmentRequest;
import com.alex.logistics.logistics_management.repository.ShipmentRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ShipmentRequestServiceTest {

    @Mock
    private ShipmentRequestRepository repository;

    private ShipmentRequestService service;

    private ShipmentRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Инициализация моков
        service = new ShipmentRequestService(repository);// Передача мока в сервис

        // Инициализация тестовых данных
        request = new ShipmentRequest();
        request.setClientName("Test Client");
        request.setCargoDescription("Test Cargo");
        request.setStatus("PENDING");
    }

    @Test
    public void testGetAllRequests() {
        // Arrange
        List<ShipmentRequest> mockRequests = List.of(request);
        when(repository.findAll()).thenReturn(mockRequests);

        // Act
        List<ShipmentRequest> result = service.getAllRequests();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Client", result.get(0).getClientName());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(request));

        // Act
        Optional<ShipmentRequest> result = service.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Test Client", result.get().getClientName());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    public void testCreateRequest() {
        // Arrange
        when(repository.save(request)).thenReturn(request);

        // Act
        ShipmentRequest result = service.createRequest(request);

        // Assert
        assertNotNull(result);
        assertEquals("Test Client", result.getClientName());
        verify(repository, times(1)).save(request);
    }

    @Test
    public void testDeleteRequest() {
        // Arrange
        doNothing().when(repository).deleteById(1L);

        // Act
        service.deleteRequest(1L);

        // Assert
        verify(repository, times(1)).deleteById(1L);
    }
}
