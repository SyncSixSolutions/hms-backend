package com.services.services.service;

import com.services.services.dto.reservationDTO.ReservationDTO;
import com.services.services.dto.reservationDTO.ReservationResponseDTO; // Add this import
import com.services.services.model.ReservationModel;
import com.services.services.repo.ReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime; // Add this import
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors; // Add this import

@Service
public class ReservationService {

    @Autowired
    private ReservationRepo reservationRepo;

    public ResponseEntity<Object> createBooking(ReservationDTO reservationDTO) {
        try {
            // Validate required fields
            if (reservationDTO.getUserId() == null || reservationDTO.getCheckInDate() == null ||
                    reservationDTO.getCheckOutDate() == null || reservationDTO.getRoomId() == null) {
                return ResponseEntity.badRequest().body(
                        Map.of("message", "Invalid request. Please provide all required fields.")
                );
            }

            // Validate dates
            if (reservationDTO.getCheckInDate().isAfter(reservationDTO.getCheckOutDate())) {
                return ResponseEntity.badRequest().body(
                        Map.of("message", "Check-out date must be after check-in date")
                );
            }

            // Map DTO to Model with defaults
            ReservationModel reservation = new ReservationModel();
            reservation.setUserId(reservationDTO.getUserId());
            reservation.setRoomId(reservationDTO.getRoomId());
            reservation.setRoomType(reservationDTO.getRoomType());
            reservation.setCheckInDate(Date.valueOf(reservationDTO.getCheckInDate()));
            reservation.setCheckOutDate(Date.valueOf(reservationDTO.getCheckOutDate()));
            reservation.setAdultsCount(reservationDTO.getAdultsCount());
            reservation.setChildrenCount(reservationDTO.getChildrenCount());
            reservation.setPaymentMethod(
                    reservationDTO.getPaymentMethod() != null ?
                            reservationDTO.getPaymentMethod() : "ONLINE"
            );
            reservation.setPaymentStatus(
                    reservationDTO.getPaymentStatus() != null ?
                            reservationDTO.getPaymentStatus() : "PENDING"
            );
            reservation.setTotalAmount(reservationDTO.getTotalAmount());
            reservation.setBookingStatus(
                    reservationDTO.getBookingStatus() != null ?
                            reservationDTO.getBookingStatus() : "CONFIRMED"
            );

            ReservationModel savedReservation = reservationRepo.save(reservation);

            // Prepare response
            Map<String, Object> response = new HashMap<>();
            response.put("reservationId", savedReservation.getReservationId());
            response.put("userId", savedReservation.getUserId());
            response.put("roomId", savedReservation.getRoomId());
            response.put("roomType", savedReservation.getRoomType());
            response.put("checkInDate", savedReservation.getCheckInDate().toLocalDate());
            response.put("checkOutDate", savedReservation.getCheckOutDate().toLocalDate());
            response.put("adultsCount", savedReservation.getAdultsCount());
            response.put("childrenCount", savedReservation.getChildrenCount());
            response.put("paymentMethod", savedReservation.getPaymentMethod());
            response.put("paymentStatus", savedReservation.getPaymentStatus());
            response.put("totalAmount", savedReservation.getTotalAmount());
            response.put("bookingStatus", savedReservation.getBookingStatus());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of("message", "An error occurred while processing the booking request: " + e.getMessage())
            );
        }
    }

    public ResponseEntity<Object> getAllBookings(String bookingStatus) {
        try {
            List<ReservationModel> bookings;

            // Validate status parameter if provided
            if (bookingStatus != null && !bookingStatus.isEmpty()) {
                if (!"PENDING".equalsIgnoreCase(bookingStatus) &&
                        !"CONFIRMED".equalsIgnoreCase(bookingStatus)) {
                    return ResponseEntity.badRequest()
                            .body(Map.of("message", "Invalid status. Use 'PENDING' or 'CONFIRMED'"));
                }
                bookings = reservationRepo.findByBookingStatus(bookingStatus.toUpperCase());
            } else {
                bookings = reservationRepo.findAll();
            }

            List<ReservationResponseDTO> response = bookings.stream()
                    .map(booking -> new ReservationResponseDTO(
                            booking.getReservationId(),
                            booking.getUserId(),
                            booking.getRoomId(),
                            booking.getRoomType(),
                            booking.getCheckInDate().toLocalDate(),
                            booking.getCheckOutDate().toLocalDate(),
                            booking.getAdultsCount(),
                            booking.getChildrenCount(),
                            booking.getPaymentMethod(),
                            booking.getPaymentStatus(),
                            booking.getTotalAmount(),
                            booking.getBookingStatus(),
                            booking.getCreatedAt().toLocalDateTime(),
                            booking.getUpdatedAt().toLocalDateTime()
                    ))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Failed to retrieve bookings: " + e.getMessage()));
        }
    }
}