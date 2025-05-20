package com.services.services.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReservationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reservationId;

    private int userId;
    private int roomId;
    private String roomType;
    private Date checkInDate;
    private Date checkOutDate;
    private int adultsCount;
    private int childrenCount;
    private String paymentMethod;
    private String paymentStatus;
    private BigDecimal totalAmount;
    private String bookingStatus;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
