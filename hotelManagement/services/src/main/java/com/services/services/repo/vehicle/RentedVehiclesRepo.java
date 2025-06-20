package com.services.services.repo.vehicle;

import com.services.services.model.vehicel.RentedVehicles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RentedVehiclesRepo extends JpaRepository<RentedVehicles, Integer>{
    @Query("SELECT r FROM RentedVehicles r WHERE r.vehicleId = :vehicleId AND " +
            "(:startDate <= r.endDate AND :endDate >= r.startDate)")
    List<RentedVehicles> findByVehicleIdAndDateRange(int vehicleId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT r.vehicleId FROM RentedVehicles r " +
            "WHERE r.vehicleId IN :vehicleIds AND " +
            "(r.startDate <= :endDate AND r.endDate >= :startDate)")
    List<Integer> findOverlappingRentals(
            @Param("vehicleIds") List<Integer> vehicleIds,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
