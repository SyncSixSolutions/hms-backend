package com.services.services.repo;

import com.services.services.model.RoomModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepo extends JpaRepository<RoomModel, Long> {

    List<RoomModel> findByRoomIdNotIn(List<Integer> roomId);
}
