package com.services.services.repo.vehicle;

import com.services.services.model.vehicel.VehicleAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VehicleAvailabilityRepo extends JpaRepository<VehicleAvailability, Integer> {
    VehicleAvailability findByVehicleId(int vehicleId);

    VehicleAvailability findByVehicleIdAndAvailabilityFromAndAvailabilityTo(int vehicleId, LocalDate availabilityFrom, LocalDate availabilityTo);

    @Query("SELECT v.vehicleId FROM VehicleAvailability v " +
            "WHERE :startDate <= v.availabilityTo AND :endDate >= v.availabilityFrom")
    List<Integer> findAvailableVehicleIdsBetweenDates(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
