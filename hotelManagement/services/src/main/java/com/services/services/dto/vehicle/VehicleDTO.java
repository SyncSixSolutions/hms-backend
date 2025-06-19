package com.services.services.dto.vehicle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {
    private int vehicleId;
    private String vehicleNumber;
    private int passengerCount;
    private String vehicleType;
    private BigDecimal pricePerKm;
    private BigDecimal basePrice;
    private LocalDate availabilityFrom;
    private LocalDate availabilityTo;
    private String description;
    private LocalDateTime createdAt;
    private int ownerId;
}
