package com.services.services.repo.vehicle;

import com.services.services.dto.vehicle.VehicleImagesDTO;
import com.services.services.model.vehicel.VehicleImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleImagesRepo extends JpaRepository<VehicleImages, Integer> {
   public List<VehicleImages> findByVehicleId(Integer vehicleId);
}
