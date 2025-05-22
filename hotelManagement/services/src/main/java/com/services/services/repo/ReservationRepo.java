package com.services.services.repo;

import com.services.services.model.ReservationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepo extends JpaRepository<ReservationModel, Integer> {
  
    @Query("SELECT r.roomId FROM ReservationModel r WHERE " +
            "r.checkInDate <= :toDate AND r.checkOutDate >= :fromDate")
    List<Integer> findReservedRoomIds(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    List<ReservationModel> findByBookingStatus(String bookingStatus);
    List<ReservationModel> findByUserIdAndBookingStatus(String userId, String bookingStatus);
    List<ReservationModel> findByUserId(String userId);
}
