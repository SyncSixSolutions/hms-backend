package com.services.services.model.reservation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "roomBooking")
public class RoomBookingModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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