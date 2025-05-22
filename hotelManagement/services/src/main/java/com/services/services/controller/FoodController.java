package com.services.services.controller;

import com.services.services.dto.FoodDTOs.AddFoodDTO;
import com.services.services.dto.FoodDTOs.FoodDTO;
import com.services.services.dto.FoodDTOs.UpdateFoodDTO;
import com.services.services.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "api/services/meals")
public class FoodController {
    @Autowired
    private FoodService foodService;

    @GetMapping
    public ResponseEntity<?> getAllFoods() {
        List<FoodDTO> foods = foodService.getAllItems();
        if (foods.isEmpty()) {
            return notFoundResponse("No food items available");
        }
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/breakfast")
    public ResponseEntity<?> getBreakfastFoods() {
        List<FoodDTO> foods = foodService.getBreakfastItems();
        if (foods.isEmpty()) {
            return notFoundResponse("No breakfast items available");
        }
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/lunch")
    public ResponseEntity<?> getLunchFoods() {
        List<FoodDTO> foods = foodService.getLunchItems();
        if (foods.isEmpty()) {
            return notFoundResponse("No lunch items available");
        }
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/dinner")
    public ResponseEntity<?> getDinnerFoods() {
        List<FoodDTO> foods = foodService.getDinnerItems();
        if (foods.isEmpty()) {
            return notFoundResponse("No dinner items available");
        }
        return ResponseEntity.ok(foods);
    }

    @PostMapping("/addMeal")
    public ResponseEntity<?> addMeal(@RequestBody AddFoodDTO addFoodDTO) {
        FoodDTO savedFood = foodService.addMeal(addFoodDTO);
        return ResponseEntity.ok(savedFood);
    }

    @PutMapping("/updateMeal/{id}")
    public ResponseEntity<?> updateMeal(@PathVariable int id, @RequestBody UpdateFoodDTO updateFoodDTO) {
        FoodDTO updatedFood = foodService.updateMeal(id, updateFoodDTO);
        return ResponseEntity.ok(updatedFood);
    }

    @DeleteMapping("/deleteMeal/{id}")
    public ResponseEntity<?> deleteMeal(@PathVariable int id) {
        foodService.deleteMeal(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Food item deleted successfully");
        return ResponseEntity.ok(response);
    }


    private ResponseEntity<Map<String, String>> notFoundResponse(String message) {
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return ResponseEntity.status(404).body(response);
    }
}