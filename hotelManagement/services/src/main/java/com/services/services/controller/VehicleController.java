package com.services.services.controller;

import com.services.services.dto.vehicle.CreateVehicleDTO;
import com.services.services.dto.vehicle.VehicleDTO;
import com.services.services.dto.vehicle.VehicleResponseDTO;
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

}
