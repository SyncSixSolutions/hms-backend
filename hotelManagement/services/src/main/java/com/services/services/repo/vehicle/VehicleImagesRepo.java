package com.services.services.repo.vehicle;

import com.services.services.model.vehicel.VehicleImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleImagesRepo extends JpaRepository<VehicleImages, Integer> {
}
