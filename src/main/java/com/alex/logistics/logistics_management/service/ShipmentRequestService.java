package com.alex.logistics.logistics_management.service;

import com.alex.logistics.logistics_management.model.ShipmentRequest;
import com.alex.logistics.logistics_management.repository.ShipmentRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ShipmentRequestService {
    private final ShipmentRequestRepository repository;

    public ShipmentRequestService(ShipmentRequestRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public List<ShipmentRequest> getAllRequests() {
        return repository.findAll();
    }

    @Transactional
    public Optional<ShipmentRequest> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public ShipmentRequest createRequest(ShipmentRequest request) {
        return repository.save(request);
    }

    @Transactional
    public void deleteRequest(Long id) {
        repository.deleteById(id);
    }
}
