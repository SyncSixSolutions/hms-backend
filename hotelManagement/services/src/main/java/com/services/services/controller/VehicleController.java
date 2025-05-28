package com.services.services.controller;

import com.services.services.dto.vehicle.*;
import com.services.services.repo.vehicle.VehicleModelRepo;
import com.services.services.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@CrossOrigin
@RequestMapping("api/v1/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping("/addVehicle")
    public String addVehicle(@RequestBody CreateVehicleDTO createVehicleDTO) {
        // Logic to save vehicle
        return vehicleService.createVehicle(createVehicleDTO);
    }

    @GetMapping("/getVehicles")
    public List<VehicleDTO> getAllVehicles(){
        return vehicleService.getAllVehicles();
    }

    @GetMapping("/getVehicle/{vehicleId}")
    public VehicleResponseDTO getVehicleById(@PathVariable int vehicleId){
        log.info("Fetching vehicle with ID: {}", vehicleId);
        return vehicleService.getVehicleById(vehicleId);
    }

    @PostMapping("/rentVehicle")
    public String rentVehicle(@RequestBody RentedVehiclesDTO rentedVehiclesDTO){
        log.info("Renting vehicle with ID: {}", rentedVehiclesDTO.getVehicleId());
        return vehicleService.rentVehicle(rentedVehiclesDTO.getUserId(), rentedVehiclesDTO.getVehicleId(), rentedVehiclesDTO.getStartDate(), rentedVehiclesDTO.getEndDate()).toString();
    }

    @PutMapping ("/updateVehicle")
    public String updateVehicle(@RequestBody UpdateVehicleDTO updateVehicleDTO) {
        // Logic to update vehicle
        return vehicleService.updateVehicle(updateVehicleDTO);
    }

}
