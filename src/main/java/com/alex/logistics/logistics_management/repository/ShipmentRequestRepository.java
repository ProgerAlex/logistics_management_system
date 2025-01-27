package com.alex.logistics.logistics_management.repository;

import com.alex.logistics.logistics_management.model.ShipmentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRequestRepository extends JpaRepository<ShipmentRequest, Long> {
}