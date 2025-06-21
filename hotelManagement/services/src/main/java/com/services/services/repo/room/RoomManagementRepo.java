package com.services.services.repo.room;

import com.services.services.model.room.RoomManagementModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomManagementRepo extends JpaRepository<RoomManagementModel, Long> {
    boolean existsByRoomNumber(String roomNumber);

    // Optional: Additional query methods for filtering
    List<RoomManagementModel> findByStatus(String status);
    List<RoomManagementModel> findByRoomType(String roomType);
    List<RoomManagementModel> findByFloor(Integer floor);
    Optional<RoomManagementModel> findByRoomNumber(String roomNumber);


    @Query("SELECT r FROM RoomManagementModel r WHERE r.pricePerNight BETWEEN :minPrice AND :maxPrice")
    List<RoomManagementModel> findByPriceRange(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);
}
