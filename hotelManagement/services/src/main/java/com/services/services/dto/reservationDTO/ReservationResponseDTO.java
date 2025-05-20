package com.services.services.dto.reservationDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponseDTO {
    private int reservationId;
    private String userId;
    private String roomId;
    private String roomType;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int adultsCount;
    private int childrenCount;
    private String paymentMethod;
    private String paymentStatus;
    private BigDecimal totalAmount;
    private String bookingStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
