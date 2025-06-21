package com.services.services.repo.room;

import com.services.services.model.room.RoomManagementModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomManagementRepo extends JpaRepository<RoomManagementModel, Long> {
    boolean existsByRoomNumber(String roomNumber);
}
