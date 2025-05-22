package com.services.services.repo;

import com.services.services.model.RoomBookingModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RoomBookingRepo extends JpaRepository<RoomBookingModel, Long> {

}
