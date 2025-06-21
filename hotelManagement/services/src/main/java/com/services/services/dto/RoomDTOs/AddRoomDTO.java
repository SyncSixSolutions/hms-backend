package com.services.services.dto.RoomDTOs;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddRoomDTO {
    private String roomNumber;
    private String roomType;
    private Double pricePerNight;
    private int capacity;
    private String status;
    private String description;
    private Integer floor;
    private String bedType;
    private String roomSize;
    private List<Long> amenityIds;
    private List<MultipartFile> images;

    // Getters and setters
}