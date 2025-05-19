package com.services.services.repo.vehicle;

import com.services.services.model.vehicel.VehicleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleModelRepo extends JpaRepository<VehicleModel, Integer> {
}
