package com.services.services.dto.reservationDTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RoomAvailabilityResponse {
    private boolean available;
    private String message;

    public RoomAvailabilityResponse(boolean available, String message) {
        this.available = available;
        this.message = message;
    }

}


