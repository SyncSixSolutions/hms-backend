package com.services.services.model.vehicel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "vehicle_owners")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class VehicleOwners {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID ownerId;
    private String name;
    private String contactNumber;
    private String nic;
}
