package com.services.services.dto.FoodDTOs;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddFoodDTO {
    private String foodName;
    private String availableTimes;
    private String foodNature;
    private BigDecimal price;
    private String description;
    private String foodPicture;
}
