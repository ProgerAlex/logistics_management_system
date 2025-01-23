package com.alex.logistics.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "shipment_requests")
public class ShipmentRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String clientName;

    @Column(nullable = false)
    private String cargoDescription;

    @Column(nullable = false)
    private String status;
}