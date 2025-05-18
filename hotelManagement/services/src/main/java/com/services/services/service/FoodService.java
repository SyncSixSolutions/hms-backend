package com.services.services.service;

import com.services.services.dto.FoodDTO;
import com.services.services.model.FoodModel;
import com.services.services.repo.FoodRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class FoodService {
    @Autowired
    private FoodRepo foodRepo;
    @Autowired
    private ModelMapper modelMapper;

    public List<FoodDTO> getAllItems() {
        List<FoodModel> itemList = foodRepo.findAll();
        return modelMapper.map(itemList, new TypeToken<List<FoodDTO>>(){}.getType());
    }

    public List<FoodDTO> getBreakfastItems() {
        List<FoodModel> items = foodRepo.findByAvailableTimeContaining("Breakfast");
        return modelMapper.map(items, new TypeToken<List<FoodDTO>>(){}.getType());
    }

    public List<FoodDTO> getLunchItems() {
        List<FoodModel> items = foodRepo.findByAvailableTimeContaining("Lunch");
        return modelMapper.map(items, new TypeToken<List<FoodDTO>>(){}.getType());
    }

    public List<FoodDTO> getDinnerItems() {
        List<FoodModel> items = foodRepo.findByAvailableTimeContaining("Dinner");
        return modelMapper.map(items, new TypeToken<List<FoodDTO>>(){}.getType());
    }
}