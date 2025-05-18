package com.services.services.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;
import java.time.LocalDate;

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
    private DecimalFormat pricePerKm;
    private DecimalFormat basePrice;
    private LocalDate availabilityFrom;
    private LocalDate availabilityTo;
    private Srting description;
    private LocalDate createdAt;
    private int ownerId;
}
