package com.services.services.dto.vehicle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleImagesDTO {
    private int imageId;
    private int vehicleId;
    private String imageUrl;
    private LocalDateTime uploadedAt;
}
