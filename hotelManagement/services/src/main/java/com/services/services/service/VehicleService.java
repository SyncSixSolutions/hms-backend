package com.services.services.service;


import com.services.services.dto.vehicle.CreateVehicleDTO;
import com.services.services.dto.vehicle.VehicleAvailabilityDTO;
import com.services.services.model.vehicel.VehicleAvailability;
import com.services.services.model.vehicel.VehicleImages;
import com.services.services.model.vehicel.VehicleModel;
import com.services.services.model.vehicel.VehicleOwners;
import com.services.services.repo.vehicle.VehicleAvailabilityRepo;
import com.services.services.repo.vehicle.VehicleImagesRepo;
import com.services.services.repo.vehicle.VehicleModelRepo;
import com.services.services.repo.vehicle.VehicleOwnersRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VehicleService {

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
        // 1. save the vehicle
        VehicleModel vehicle = modelMapper.map(createVehicleDTO.getVehicle(), VehicleModel.class);
        vehicle = vehicleRepo.save(vehicle);

        // preparing entities for save
        List<VehicleImages> imageEntities = createVehicleDTO.getImages().stream()
                .map(dto -> modelMapper.map(dto, VehicleImages.class))
                .toList();

        VehicleOwners vehicleOwners = modelMapper.map(createVehicleDTO.getOwner(), VehicleOwners.class);

        VehicleAvailability availabilityEntity = modelMapper.map(createVehicleDTO.getAvailability(), VehicleAvailability.class);

        // 2. Save related entities
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
        availabilityRepo.save(availability);
    }




}
