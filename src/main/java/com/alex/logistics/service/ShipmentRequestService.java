package com.alex.logistics.service;

import com.alex.logistics.model.ShipmentRequest;
import com.alex.logistics.repository.ShipmentRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipmentRequestService {
    private final ShipmentRequestRepository repository;

    public ShipmentRequestService(ShipmentRequestRepository repository) {
        this.repository = repository;
    }

    public List<ShipmentRequest> getAllRequests() {
        return repository.findAll();
    }

    public ShipmentRequest createRequest(ShipmentRequest request) {
        return repository.save(request);
    }
}
