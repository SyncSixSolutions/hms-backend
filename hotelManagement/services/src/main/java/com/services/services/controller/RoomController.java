package com.services.services.controller;

import com.services.services.dto.RoomDTOs.AddRoomDTO;
import com.services.services.dto.RoomDTOs.RoomManagementDTO;
import com.services.services.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/services/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping("/add")
    public ResponseEntity<RoomManagementDTO> addRoom(@ModelAttribute AddRoomDTO dto) {
        return ResponseEntity.ok(roomService.addRoom(dto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<RoomManagementDTO>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    // Optional: Get rooms by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<RoomManagementDTO>> getRoomsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(roomService.getRoomsByStatus(status));
    }

    // Optional: Get rooms by type
    @GetMapping("/type/{roomType}")
    public ResponseEntity<List<RoomManagementDTO>> getRoomsByType(@PathVariable String roomType) {
        return ResponseEntity.ok(roomService.getRoomsByType(roomType));
    }

    // Optional: Get rooms by floor
    @GetMapping("/floor/{floor}")
    public ResponseEntity<List<RoomManagementDTO>> getRoomsByFloor(@PathVariable Integer floor) {
        return ResponseEntity.ok(roomService.getRoomsByFloor(floor));
    }


    @DeleteMapping("/delete/{roomNumber}")
    public ResponseEntity<String> deleteRoomByNumber(@PathVariable String roomNumber) {
        roomService.deleteRoomByNumber(roomNumber);
        return ResponseEntity.ok("Room deleted successfully");
    }
}
