package com.services.services.repo.vehicle;

import com.services.services.dto.vehicle.VehicleOwnersDTO;
import com.services.services.model.vehicel.VehicleOwners;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleOwnersRepo extends JpaRepository<VehicleOwners, Integer> {
//    VehicleOwners findByVehicleId(int vehicleId);
    VehicleOwners findByVehicleId(int vehicleId);

    VehicleOwners findByNic(String nic);
}
