package com.services.services.model.vehicel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "vehicle_availability")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class VehicleAvailability {
    @Id
    private UUID slotId;
    private int vehicleId;
    private LocalDate availabilityFrom;
    private LocalDate availabilityTo;
    private LocalDateTime createdAt;
}
