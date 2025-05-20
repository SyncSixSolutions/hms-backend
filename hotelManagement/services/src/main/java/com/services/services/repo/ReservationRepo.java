package com.services.services.repo;

import com.services.services.model.ReservationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepo extends JpaRepository<ReservationModel, Integer> {
    List<ReservationModel> findByBookingStatus(String bookingStatus);
}