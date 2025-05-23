package com.services.services.model.vehicel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "rented_vehicles")
@AllArgsConstructor
@NoArgsConstructor
public class RentedVehicles {
    @Id
    private int rentalId;
    private int userId;
    private int vehicleId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal price;
    private LocalDateTime createdAt;

}
