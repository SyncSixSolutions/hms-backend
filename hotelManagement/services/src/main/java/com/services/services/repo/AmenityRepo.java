package com.services.services.repo;

import com.services.services.model.room.AmenityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmenityRepo extends JpaRepository<AmenityModel, Long> {
}
