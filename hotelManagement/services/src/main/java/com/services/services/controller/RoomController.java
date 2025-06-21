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
}