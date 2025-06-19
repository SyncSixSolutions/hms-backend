package com.services.services.service;


import com.services.services.dto.vehicle.*;
import com.services.services.model.vehicel.VehicleAvailability;
import com.services.services.model.vehicel.VehicleImages;
import com.services.services.model.vehicel.VehicleModel;
import com.services.services.model.vehicel.VehicleOwners;
import com.services.services.repo.vehicle.VehicleAvailabilityRepo;
import com.services.services.repo.vehicle.VehicleImagesRepo;
import com.services.services.repo.vehicle.VehicleModelRepo;
import com.services.services.repo.vehicle.VehicleOwnersRepo;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class VehicleService {

    private static final Logger log = LoggerFactory.getLogger(VehicleService.class);
    @Autowired
    private VehicleModelRepo vehicleRepo;

    @Autowired
    private VehicleAvailabilityRepo availabilityRepo;

    @Autowired
    private VehicleImagesRepo imagesRepo;

    @Autowired
    private VehicleOwnersRepo ownersRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(rollbackFor = Exception.class)
    public String createVehicle(CreateVehicleDTO createVehicleDTO) {
        // Validating the input data if anything is not initialized that will throw an error
        if (createVehicleDTO.getVehicle() == null || createVehicleDTO.getOwner() == null || createVehicleDTO.getImages() == null || createVehicleDTO.getAvailability() == null) {
            throw new IllegalArgumentException("Invalid input data");
        }

        VehicleModel vehicle = modelMapper.map(createVehicleDTO.getVehicle(), VehicleModel.class);

        // check if the vehicle and the time slot already exists for that vehicle
        VehicleAvailabilityDTO availabilityDTO = createVehicleDTO.getAvailability();
        VehicleAvailability existingAvailability = availabilityRepo.findByVehicleIdAndAvailabilityFromAndAvailabilityTo(
                vehicle.getVehicleId(),
                availabilityDTO.getAvailabilityFrom(),
                availabilityDTO.getAvailabilityTo()
        );

        if (existingAvailability != null) {
            log.info("Vehicle availability already exists for the given time slot");
            throw new RuntimeException("Vehicle availability already exists for the given time slot");
        }

        // check if the vehicle already exists
        // if this becomes yes then we can edit the vehicle in different way
        // vehicle need to find by vehicleType, and vehicle number and owner info
        VehicleModel existingVehicle = vehicleRepo.findByVehicleTypeAndVehicleNumberAndOwnerId(
                vehicle.getVehicleType(),
                vehicle.getVehicleNumber(),
                vehicle.getOwnerId()
        );
        if (existingVehicle != null) {
            log.info("Vehicle already exists. Try with edit vehicle");
            throw new RuntimeException("Vehicle already exists with the given ID");
        }

        // preparing entities for save
        List<VehicleImages> imageEntities = createVehicleDTO.getImages().stream()
                .map(dto -> modelMapper.map(dto, VehicleImages.class))
                .toList();
        VehicleOwners vehicleOwners = modelMapper.map(createVehicleDTO.getOwner(), VehicleOwners.class);
        VehicleAvailability availabilityEntity = modelMapper.map(createVehicleDTO.getAvailability(), VehicleAvailability.class);

        // Save related entities
        // add the createdAt field dynamically for the vehicle
        vehicle.setCreatedAt(LocalDateTime.now());
        vehicle = vehicleRepo.save(vehicle);

        saveImages(vehicle, imageEntities);
        saveOwner(vehicleOwners);
        saveAvailability(vehicle, availabilityEntity);

        return "Vehicle created successfully";
    }

    private void saveImages(VehicleModel vehicle, List<VehicleImages> images) {
        for (VehicleImages image : images) {
            image.setVehicleId(vehicle.getVehicleId());
            imagesRepo.save(image);
        }
    }

    private void saveOwner(VehicleOwners owner) {
        ownersRepo.save(owner);
    }

    private void saveAvailability(VehicleModel vehicle, VehicleAvailability availability) {
        availability.setVehicleId(vehicle.getVehicleId());
        availability.setCreatedAt(LocalDateTime.now());
        availabilityRepo.save(availability);
    }

    public List<VehicleDTO> getAllVehicles() {
        List<VehicleModel> vehicles = vehicleRepo.findAll();
        return vehicles.stream()
                .map(vehicle -> modelMapper.map(vehicle, VehicleDTO.class))
                .toList();
    }

    public VehicleResponseDTO getVehicleById(int vehicleId) {
        VehicleModel vehicle = vehicleRepo.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with ID: " + vehicleId));
        // we have to add details for the vehicle like availability, images and owner
        VehicleAvailability availability = availabilityRepo.findByVehicleId(vehicleId);
        List<VehicleImages> images = imagesRepo.findByVehicleId(vehicleId);
        VehicleOwners owner = ownersRepo.findByVehicleId(vehicleId);

        VehicleResponseDTO responseDTO = new VehicleResponseDTO();

        // mapping the vehicle to DTO
        responseDTO.setVehicle(modelMapper.map(vehicle, VehicleDTO.class));
        responseDTO.setAvailability(modelMapper.map(availability, VehicleAvailabilityDTO.class));
        responseDTO.setImages(images.stream()
                .map(image -> modelMapper.map(image, VehicleImagesDTO.class))
                .toList());
        responseDTO.setOwner(modelMapper.map(owner, VehicleOwnersDTO.class));
        return responseDTO;
    }


}
