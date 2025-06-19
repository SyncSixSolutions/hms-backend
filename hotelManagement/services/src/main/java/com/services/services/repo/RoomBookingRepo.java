package com.services.services.repo;

import com.services.services.model.reservation.RoomBookingModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomBookingRepo extends JpaRepository<RoomBookingModel, Long> {
}
