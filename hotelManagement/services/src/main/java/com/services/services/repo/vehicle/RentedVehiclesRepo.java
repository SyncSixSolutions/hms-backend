package com.services.services.repo.vehicle;

import com.services.services.model.vehicel.RentedVehicles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentedVehiclesRepo extends JpaRepository<RentedVehicles, Integer>{
}
