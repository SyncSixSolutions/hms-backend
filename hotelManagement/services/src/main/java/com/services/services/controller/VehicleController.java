package com.services.services.controller;

import com.services.services.dto.vehicle.*;
import com.services.services.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("api/v1/services/vehicle")
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

//    @PutMapping ("/updateVehicle")
//    public String updateVehicle(@RequestBody UpdateVehicleDTO updateVehicleDTO) {
//        // Logic to update vehicle
//        return vehicleService.updateVehicle(updateVehicleDTO);
//    }

    @GetMapping("/getOwners")
    public List<VehicleOwnersDTO> getOwners(){
        return vehicleService.getAllOwners();
    }

    // get the details of a existing vehicle by passing from and to date
    @GetMapping("/getVehiclesByDateRange")
    public List<VehicleResponseDTO> getVehiclesByDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        log.info("Fetching vehicles between {} and {}", startDate, endDate);
        return vehicleService.getVehiclesByDateRange(startDate, endDate);
    }

}
