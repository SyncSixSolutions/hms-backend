package com.services.services.service;

import com.services.services.dto.FoodDTOs.AddFoodDTO;
import com.services.services.dto.FoodDTOs.FoodDTO;
import com.services.services.dto.FoodDTOs.UpdateFoodDTO;
import com.services.services.model.food.FoodModel;
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

    public FoodDTO addMeal(AddFoodDTO dto) {
        FoodModel model = modelMapper.map(dto, FoodModel.class);
        FoodModel saved = foodRepo.save(model);
        return modelMapper.map(saved, FoodDTO.class);
    }

    public FoodDTO updateMeal(int id, UpdateFoodDTO dto) {
        FoodModel existing = foodRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Food not found"));
        modelMapper.map(dto, existing); // update existing with new values
        FoodModel updated = foodRepo.save(existing);
        return modelMapper.map(updated, FoodDTO.class);
    }

    public void deleteMeal(int id) {
        if (!foodRepo.existsById(id)) {
            throw new RuntimeException("Food not found");
        }
        foodRepo.deleteById(id);
    }

}