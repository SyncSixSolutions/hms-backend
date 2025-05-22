package com.services.services.dto.reservationDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RoomDTO {
    private int roomId;
    private boolean availability = true;
    private String roomNumber;
    private Integer floor;
    private String roomType;
    private Integer capacity;
    private BigDecimal pricePerNight;
    private String bedType;
    private String roomSize;
    private String Description;
    private Timestamp createAt;

    public RoomDTO(Long roomId, boolean availability, BigDecimal pricePerNight, String description, Integer floor, String roomType, String roomSize, String roomType1, String roomNumber, String bedType, Integer capacity, Timestamp createAt) {
    }
}

