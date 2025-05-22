package com.services.services.dto.reservationDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomBookingDTO {

    private Long bookingId;
    private Integer userId;
    private Integer roomId;
    private String roomType;
    private Date checkInDate;
    private Date checkOutDate;
    private Integer adultCount;
    private Integer childrenCount;
    private String status;
    private Timestamp createdAt;
}
