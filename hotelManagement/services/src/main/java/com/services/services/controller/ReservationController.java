package com.services.services.controller;

import com.services.services.dto.reservationDTO.ReservationDTO;
import com.services.services.dto.reservationDTO.RoomDTO;
import com.services.services.dto.reservationDTO.RoomAvailabilityResponse;
import com.services.services.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/services")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/createBooking")
    public Object createBooking(@RequestBody ReservationDTO reservationDTO) {
        return reservationService.createBooking(reservationDTO);
    }

    @GetMapping("/getBookings")
    public ResponseEntity<Object> getBookings(
            @RequestParam(required = false) String status) {

        // Validate status if provided
        if (status != null && !status.isEmpty()) {
            if (!status.equalsIgnoreCase("PENDING") &&
                    !status.equalsIgnoreCase("CONFIRMED")) {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "Invalid status parameter. Use 'PENDING' or 'CONFIRMED'"));
            }
        }

        return reservationService.getAllBookings(status);
    }

    @GetMapping("/getBookings/{userId}")
    public ResponseEntity<Object> getBookingsByUserId(
            @PathVariable String userId,
            @RequestParam(required = false) String status) {

        // Validate userId
        if (userId == null || userId.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Invalid request. Please provide a valid userId."));
        }

        return reservationService.getBookingsByUserId(userId, status);
    }

    @GetMapping("/getAvailableRoomsOnDate")
    public ResponseEntity<?> getAvailableRooms(
            @RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate,
            @RequestParam("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate) {

        if (fromDate == null || toDate == null || fromDate.after(toDate)) {
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid request. Please provide a valid fromDate and toDate."));
        }

        List<RoomDTO> availableRooms = reservationService.getAvailableRooms(fromDate, toDate);

        boolean anyAvailable = availableRooms.stream().anyMatch(RoomDTO::isAvailability);

        if (!anyAvailable) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "No rooms available for the selected date range."));
        }

        return ResponseEntity.ok(Map.of("rooms", availableRooms));
    }

    @GetMapping("/getAvailabilityOfARoom")
    public ResponseEntity<RoomAvailabilityResponse> checkRoomAvailability(
            @RequestParam int roomId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate) {

        if (fromDate == null || toDate == null || roomId <= 0 || fromDate.after(toDate)) {
            return ResponseEntity.badRequest().body(
                    new RoomAvailabilityResponse(false, "Invalid request. Please provide valid roomId, fromDate, and toDate."));
        }

        RoomAvailabilityResponse response = reservationService.checkAvailability(roomId, fromDate, toDate);

        if (response.isAvailable()) {
            return ResponseEntity.ok(response); // 200 OK
        } else {
            return ResponseEntity.status(404).body(response); // 404 Not Available
        }
    }
}