package com.alex.logistics.logistics_management.repository;

import com.alex.logistics.model.ShipmentRequest;
import com.alex.logistics.repository.ShipmentRequestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ShipmentRequestRepositoryTest {

    @Autowired
    private ShipmentRequestRepository repository;

    @Test
    public void testSaveShipmentRequest() {
        // Arrange
        ShipmentRequest request = new ShipmentRequest();
        request.setClientName("Test Client");
        request.setCargoDescription("Test Cargo");
        request.setStatus("PENDING");

        // Act
        ShipmentRequest savedRequest = repository.save(request);

        // Assert
        assertThat(savedRequest).isNotNull();
        assertThat(savedRequest.getId()).isNotNull();
        assertThat(savedRequest.getClientName()).isEqualTo("Test Client");
        assertThat(savedRequest.getCargoDescription()).isEqualTo("Test Cargo");
        assertThat(savedRequest.getStatus()).isEqualTo("PENDING");

        assertThat(repository.findById(savedRequest.getId()).isPresent()).isEqualTo(true);
        assertThat(repository.findById(savedRequest.getId()).get()).isEqualTo(savedRequest);
    }

    @Test
    public void testFindShipmentRequestById() {
        // Arrange
        ShipmentRequest request = new ShipmentRequest();
        request.setClientName("Test Client");
        request.setCargoDescription("Test Cargo");
        request.setStatus("PENDING");
        ShipmentRequest savedRequest = repository.save(request);

        // Act
        ShipmentRequest foundRequest = repository.findById(savedRequest.getId()).orElse(null);

        // Assert
        assertThat(foundRequest).isNotNull();
        assertThat(foundRequest.getId()).isEqualTo(savedRequest.getId());
        assertThat(foundRequest.getClientName()).isEqualTo("Test Client");
    }

    @Test
    public void testDeleteShipmentRequest() {
        // Arrange
        ShipmentRequest request = new ShipmentRequest();
        request.setClientName("Test Client");
        request.setCargoDescription("Test Cargo");
        request.setStatus("PENDING");
        ShipmentRequest savedRequest = repository.save(request);

        // Act
        repository.deleteById(savedRequest.getId());
        ShipmentRequest foundRequest = repository.findById(savedRequest.getId()).orElse(null);

        // Assert
        assertThat(foundRequest).isNull();
    }

    @Test
    public void testUpdateShipmentRequest() {
        // Arrange
        ShipmentRequest request = new ShipmentRequest();
        request.setClientName("Test Client");
        request.setCargoDescription("Test Cargo");
        request.setStatus("PENDING");
        ShipmentRequest savedRequest = repository.save(request);

        // Act
        savedRequest.setStatus("SHIPPED");
        ShipmentRequest updatedRequest = repository.save(savedRequest);

        // Assert
        assertThat(updatedRequest).isNotNull();
        assertThat(updatedRequest.getStatus()).isEqualTo("SHIPPED");
    }
}
