package com.services.services.controller;

import com.services.services.dto.FoodDTO;
import com.services.services.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/v1/")
public class FoodController {
    @Autowired
    private FoodService foodService;

    @GetMapping("/getitems")
    public List<FoodDTO> getItems() {
        return foodService.getAllItems();
    }
}
