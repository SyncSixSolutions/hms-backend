package com.services.services.model.vehicel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "vehicles")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class VehicleModel {
    @Id
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
