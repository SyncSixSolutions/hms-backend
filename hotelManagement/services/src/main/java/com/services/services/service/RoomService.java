package com.services.services.service;

import com.services.services.dto.RoomDTOs.AddRoomDTO;
import com.services.services.dto.RoomDTOs.RoomManagementDTO;
import com.services.services.model.room.AmenityModel;
import com.services.services.model.room.RoomManagementModel;
import com.services.services.repo.AmenityRepo;
import com.services.services.repo.room.RoomManagementRepo;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private RoomManagementRepo roomRepo;

    @Autowired
    private AmenityRepo amenityRepo;

    @Autowired
    private ModelMapper modelMapper;

    private static final String BASE_UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

    public RoomManagementDTO addRoom(@NotNull AddRoomDTO dto) {
        if (roomRepo.existsByRoomNumber(dto.getRoomNumber())) {
            throw new RuntimeException("Room number already exists");
        }

        // ✅ Safe fetch of amenities
        List<AmenityModel> amenities = new ArrayList<>();
        if (dto.getAmenityIds() != null && !dto.getAmenityIds().isEmpty()) {
            amenities = amenityRepo.findAllById(dto.getAmenityIds());
        }

        // ✅ Handle image uploads (max 5)
        List<String> imagePaths = new ArrayList<>();
        if (dto.getImages() != null && dto.getImages().size() <= 5) {
            String roomFolderPath = BASE_UPLOAD_DIR + dto.getRoomNumber() + "/";
            File roomFolder = new File(roomFolderPath);

            if (!roomFolder.exists() && !roomFolder.mkdirs()) {
                throw new RuntimeException("Failed to create directory: " + roomFolderPath);
            }

            for (MultipartFile image : dto.getImages()) {
                if (!Objects.requireNonNull(image.getContentType()).startsWith("image/")) {
                    throw new RuntimeException("Invalid file type: " + image.getOriginalFilename());
                }

                String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                File file = new File(roomFolderPath + fileName);
                try {
                    image.transferTo(file);
                    imagePaths.add("uploads/" + dto.getRoomNumber() + "/" + fileName);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to save image: " + fileName, e);
                }
            }
        } else if (dto.getImages() != null) {
            throw new RuntimeException("You can upload up to 5 images only.");
        }

        // Map DTO to Entity
        RoomManagementModel room = new RoomManagementModel();
        room.setRoomNumber(dto.getRoomNumber());
        room.setRoomType(dto.getRoomType());
        room.setPricePerNight(dto.getPricePerNight());
        room.setCapacity(dto.getCapacity());
        room.setStatus(dto.getStatus());
        room.setDescription(dto.getDescription());
        room.setFloor(dto.getFloor());
        room.setBedType(dto.getBedType());
        room.setRoomSize(dto.getRoomSize());
        room.setAmenities(amenities);
        room.setImageUrls(imagePaths);

        RoomManagementModel saved = roomRepo.save(room);

        return convertToDTO(saved);
    }

    public List<RoomManagementDTO> getAllRooms() {
        List<RoomManagementModel> rooms = roomRepo.findAll();
        return rooms.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Add these methods to RoomService class

    public List<RoomManagementDTO> getRoomsByStatus(String status) {
        List<RoomManagementModel> rooms = roomRepo.findByStatus(status);
        return rooms.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<RoomManagementDTO> getRoomsByType(String roomType) {
        List<RoomManagementModel> rooms = roomRepo.findByRoomType(roomType);
        return rooms.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<RoomManagementDTO> getRoomsByFloor(Integer floor) {
        List<RoomManagementModel> rooms = roomRepo.findByFloor(floor);
        return rooms.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void deleteRoomByNumber(String roomNumber) {
        RoomManagementModel room = roomRepo.findByRoomNumber(roomNumber)
                .orElseThrow(() -> new RuntimeException("Room not found with number: " + roomNumber));
        roomRepo.delete(room);
    }


    private RoomManagementDTO convertToDTO(RoomManagementModel room) {
        RoomManagementDTO dto = new RoomManagementDTO();
        dto.setRoomNumber(room.getRoomNumber());
        dto.setRoomFloor("Floor " + room.getFloor());
        dto.setReservationStatus(room.getStatus());
        dto.setRoomType(room.getRoomType());
        dto.setCapacity(room.getCapacity());
        dto.setPricePerNight(room.getPricePerNight());
        dto.setBedType(room.getBedType());
        dto.setRoomSize(room.getRoomSize());
        dto.setDescription(room.getDescription());

        // Convert amenities to Map<String, Boolean>
        Map<String, Boolean> amenityMap = new HashMap<>();
        if (room.getAmenities() != null) {
            amenityMap = room.getAmenities().stream()
                    .collect(Collectors.toMap(AmenityModel::getName, a -> true));
        }
        dto.setAmenities(amenityMap);

        // Set image URLs
        dto.setImageUrls(room.getImageUrls() != null ? room.getImageUrls() : new ArrayList<>());

        return dto;
    }
}
