package com.services.services.controller;

import com.services.services.dto.vehicle.CreateVehicleDTO;
import com.services.services.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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

}
