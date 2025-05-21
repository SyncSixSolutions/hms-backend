package com.services.services.dto.vehicle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleOwnersDTO {
    private int ownerId;
    private int vehicleId;
    private String name;
    private String contactNumber;
    private String nic;
}
