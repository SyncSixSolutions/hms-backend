package com.services.services.controller;

import com.services.services.dto.FoodOrdersDTO;
import com.services.services.service.FoodOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/services/orders")
public class FoodOrdersController {

    @Autowired
    private FoodOrdersService foodOrdersService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody FoodOrdersDTO orderDTO) {
        try {
            FoodOrdersDTO savedOrder = foodOrdersService.createOrder(orderDTO);
            return ResponseEntity.ok(savedOrder);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create order: " + e.getMessage());
        }
    }
}
