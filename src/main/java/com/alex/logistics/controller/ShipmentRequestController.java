package com.alex.logistics.controller;

import com.alex.logistics.model.ShipmentRequest;
import com.alex.logistics.service.ShipmentRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentRequestController {
    private final ShipmentRequestService service;

    public ShipmentRequestController(ShipmentRequestService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ShipmentRequest>> getAllRequests() {
        return ResponseEntity.ok(service.getAllRequests());
    }

    @PostMapping
    public ResponseEntity<ShipmentRequest> createRequest(@RequestBody ShipmentRequest request) {
        return ResponseEntity.ok(service.createRequest(request));
    }
}