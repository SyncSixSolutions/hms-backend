package com.services.services.model.vehicel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "vehicle_images")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class VehicleImages {
    @Id
    private UUID imageId;
    private int vehicleId;
    private String imageUrl;
    private LocalDateTime uploadedAt;
}
