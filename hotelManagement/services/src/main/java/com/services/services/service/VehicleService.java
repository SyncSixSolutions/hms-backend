package com.services.services.service;


import com.services.services.dto.vehicle.CreateVehicleDTO;
import com.services.services.dto.vehicle.VehicleAvailabilityDTO;
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

import java.util.List;

@Service
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

    public String createVehicle(CreateVehicleDTO createVehicleDTO) {
        VehicleModel vehicle = modelMapper.map(createVehicleDTO.getVehicle(), VehicleModel.class);
        vehicle = vehicleRepo.save(vehicle);

        // 2. Save related entities
        saveImages(vehicle, createVehicleDTO.getImages());
        saveOwner(createVehicleDTO.getOwner());
        saveAvailability(vehicle, createVehicleDTO.getAvailability());

        return "Vehicle created successfully";
    }

    private void saveImages(VehicleModel vehicle, List<VehicleImages> images) {
        for (VehicleImages image : images) {
            image.setVehicleId(vehicle.getVehicleId());
            imagesRepo.save(image);
        }
    }

    private void saveOwner(VehicleOwners owner) {
//        owner.setVehicleId(vehicle.getVehicleId());
        ownersRepo.save(owner);
    }

    private void saveAvailability(VehicleModel vehicle, VehicleAvailabilityDTO availability) {
        availability.setVehicleId(vehicle.getVehicleId());
        availabilityRepo.save(availability);
    }




}
