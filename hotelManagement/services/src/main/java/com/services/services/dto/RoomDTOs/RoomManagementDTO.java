package com.services.services.dto.RoomDTOs;

import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomManagementDTO {
    private String roomNumber;
    private String roomFloor;
    private String reservationStatus;
    private String roomType;
    private int capacity;
    private double pricePerNight;
    private String bedType;
    private String roomSize;
    private String description;
    private Map<String, Boolean> amenities;
    private List<String> imageUrls;
}
