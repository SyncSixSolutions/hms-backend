package com.services.services.controller;

import com.services.services.dto.FoodOrdersDTO;
import com.services.services.service.FoodOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/services/meals")
public class FoodOrdersController {

    @Autowired
    private FoodOrdersService foodOrdersService;

    /**
     * Create a new order.
     * @param orderDTO - The order data.
     * @return The created order.
     */
    @PostMapping("/createOrder")
    public ResponseEntity<?> createOrder(@RequestBody FoodOrdersDTO orderDTO) {
        try {
            FoodOrdersDTO savedOrder = foodOrdersService.createOrder(orderDTO);
            return ResponseEntity.ok(savedOrder);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create order: " + e.getMessage());
        }
    }

    /**
     * Get all orders.
     * @return A list of all orders.
     */
    @GetMapping("/getAllOrders")
    public ResponseEntity<?> getAllFoodOrders(){
        try{
            List<FoodOrdersDTO> orders=foodOrdersService.getAllFoodOrders();
            return ResponseEntity.ok(orders);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Failed to retrieve orders: " + e.getMessage());
        }
    }

}
