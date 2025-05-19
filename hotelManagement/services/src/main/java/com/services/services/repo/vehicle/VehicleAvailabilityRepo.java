package com.services.services.repo.vehicle;

import com.services.services.model.vehicel.VehicleAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleAvailabilityRepo extends JpaRepository<VehicleAvailability, Integer> {
}
