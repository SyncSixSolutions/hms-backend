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

        // ✅ Map DTO to Entity
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

        // ✅ Prepare DTO for response
        RoomManagementDTO response = new RoomManagementDTO();
        response.setRoomNumber(saved.getRoomNumber());
        response.setRoomFloor("Floor " + saved.getFloor());
        response.setReservationStatus(saved.getStatus());
        response.setRoomType(saved.getRoomType());
        response.setCapacity(saved.getCapacity());
        response.setPricePerNight(saved.getPricePerNight());
        response.setBedType(saved.getBedType());
        response.setRoomSize(saved.getRoomSize());
        response.setDescription(saved.getDescription());

        Map<String, Boolean> amenityMap = saved.getAmenities().stream()
                .collect(Collectors.toMap(AmenityModel::getName, a -> true));

        response.setAmenities(amenityMap);
        response.setImageUrls(saved.getImageUrls());

        return response;
    }
}
