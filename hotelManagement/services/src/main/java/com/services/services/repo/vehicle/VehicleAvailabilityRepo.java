package com.services.services.repo.vehicle;

import com.services.services.model.vehicel.VehicleAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleAvailabilityRepo extends JpaRepository<VehicleAvailability, Integer> {
}
