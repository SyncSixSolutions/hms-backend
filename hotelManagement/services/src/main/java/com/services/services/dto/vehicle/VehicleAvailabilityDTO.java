package com.services.services.dto.vehicle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleAvailabilityDTO {
    private int slotId;
    private int vehicleId;
    private LocalDate availabilityFrom;
    private LocalDate availabilityTo;
    private LocalDateTime createdAt;
}
