package com.services.services.dto.FoodDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodDTO {
    private int foodNumber;
    private String foodName;
    private String availableTimes;
    private String foodNature;
    private BigDecimal price;
    private String description;
    private String foodPicture;
    private Timestamp createdAt;
}