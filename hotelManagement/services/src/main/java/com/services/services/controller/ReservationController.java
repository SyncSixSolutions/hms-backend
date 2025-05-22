package com.services.services.controller.reservationController;

import com.services.services.dto.reservationDTO.ReservationDTO;
import com.services.services.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}