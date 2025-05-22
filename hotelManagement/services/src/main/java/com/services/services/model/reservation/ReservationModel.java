package com.services.services.model.reservation;

import jakarta.persistence.*;
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
@Table(name = "reservation")
public class ReservationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reservationId;
    private String userId;
    private String roomId;
    private String roomType;
    private Date checkInDate;
    private Date checkOutDate;
    private int adultsCount;
    private int childrenCount;
    private String paymentMethod = "ONLINE"; // Default value
    private String paymentStatus = "PENDING"; // Default value
    private BigDecimal totalAmount;
    private String bookingStatus = "CONFIRMED"; // Default value
    private Timestamp createdAt;
    private Timestamp updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Timestamp(System.currentTimeMillis());
        updatedAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Timestamp(System.currentTimeMillis());
    }
}