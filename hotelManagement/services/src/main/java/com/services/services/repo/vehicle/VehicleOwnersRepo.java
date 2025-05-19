package com.services.services.repo.vehicle;

import com.services.services.model.vehicel.VehicleOwners;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleOwnersRepo extends JpaRepository<VehicleOwners, Integer> {
}
