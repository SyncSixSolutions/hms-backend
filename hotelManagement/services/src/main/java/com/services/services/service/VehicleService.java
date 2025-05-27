package com.services.services.service;

import com.services.services.dto.vehicle.CreateVehicleDTO;
import com.services.services.dto.vehicle.RentedVehiclesDTO;
import com.services.services.dto.vehicle.VehicleAvailabilityDTO;
import com.services.services.dto.vehicle.VehicleDTO;
import com.services.services.model.vehicel.*;
import com.services.services.repo.vehicle.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

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

    @Autowired
    private RentedVehiclesRepo rentedVehiclesRepository;

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

    public RentedVehiclesDTO rentVehicle(int userId, int vehicleId, LocalDate startDate, LocalDate endDate) {
        // Validate dates
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }

        // Check vehicle existence
        VehicleModel vehicleOpt = vehicleRepo.findByVehicleId(vehicleId);
        if (vehicleOpt == null) {
            throw new IllegalArgumentException("Vehicle not found.");
        }
//        VehicleModel vehicle = vehicleOpt.get();

        // Check availability
        List<RentedVehicles> conflicts = rentedVehiclesRepository
                .findByVehicleIdAndDateRange(vehicleId, startDate, endDate);
        if (!conflicts.isEmpty()) {
            throw new IllegalStateException("Vehicle is not available in the selected date range.");
        }

        // Calculate price
        long rentalDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        BigDecimal totalPrice = vehicleOpt.getBasePrice().multiply(BigDecimal.valueOf(rentalDays));

        // Create rental record
        RentedVehicles rented = new RentedVehicles();
        rented.setRentalId(ThreadLocalRandom.current().nextInt(100000, 999999)); // or use sequence
        rented.setUserId(userId);
        rented.setVehicleId(vehicleId);
        rented.setStartDate(startDate);
        rented.setEndDate(endDate);
        rented.setPrice(totalPrice);
        rented.setCreatedAt(LocalDateTime.now());

        rentedVehiclesRepository.save(rented);

        return new RentedVehiclesDTO(
                rented.getRentalId(),
                rented.getUserId(),
                rented.getVehicleId(),
                rented.getStartDate(),
                rented.getEndDate(),
                rented.getPrice(),
                rented.getCreatedAt()
        );




}
}
