package com.alex.logistics.repository;

import com.alex.logistics.model.ShipmentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRequestRepository extends JpaRepository<ShipmentRequest, Long> {
}