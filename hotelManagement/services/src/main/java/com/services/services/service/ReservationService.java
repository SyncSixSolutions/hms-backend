package com.services.services.service;

import com.services.services.dto.reservationDTO.RoomAvailabilityResponse;
import com.services.services.dto.reservationDTO.RoomBookingDTO;
import com.services.services.dto.reservationDTO.RoomDTO;
import com.services.services.model.ReservationModel;
import com.services.services.model.RoomBookingModel;
import com.services.services.model.RoomModel;
import com.services.services.repo.ReservationRepo;
import com.services.services.repo.RoomBookingRepo;
import com.services.services.repo.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    @Autowired
    private RoomRepo roomRepository;

    @Autowired
    private ReservationRepo reservationRepo;

    public List<RoomDTO> getAvailableRooms(Date fromDate, Date toDate) {
        List<Integer> bookedRoomIds = reservationRepo.findBookedRoomIds(fromDate, toDate);

        List<RoomModel> availableRooms;
        if (bookedRoomIds.isEmpty()) {
            availableRooms = roomRepository.findAll();
        } else {
            availableRooms = roomRepository.findByRoomIdNotIn(bookedRoomIds);
        }
        return availableRooms.stream()
                .map(room -> {
                    RoomDTO dto = new RoomDTO();
                    dto.setRoomId(Math.toIntExact(room.getRoomId()));
                    dto.setPricePerNight(room.getPricePerNight());
                    dto.setDescription(room.getDescription());
                    dto.setFloor(room.getFloor());
                    dto.setRoomType(room.getRoomType());
                    dto.setCapacity(room.getCapacity());
                    dto.setAvailability(true);
                    return dto;
                })
                .collect(Collectors.toList());
    }
    public RoomAvailabilityResponse checkAvailability(int roomId, Date fromDate, Date toDate) {
        // Reservation table එකෙන් overlap වෙනවද කියලා බලනවා
        List<ReservationModel> overlappingReservations = reservationRepo
                .findOverlappingReservations(roomId, fromDate, toDate);

        if (overlappingReservations.isEmpty()) {
            // එකත් reservations නැතිනම් => available
            return new RoomAvailabilityResponse(true, "Room is available for the selected date range.");
        } else {
            // overlap වෙලා තියෙනවා => unavailable
            return new RoomAvailabilityResponse(false, "Room not available for the selected date range.");
        }
    }
}

