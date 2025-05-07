package com.services.services.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FoodModel {
    @Id
    private int foodNumber;
    private String foodName;
    private String availableTimes;
    private String foodNature;
    private BigDecimal price;
    private String description;
    private String foodPicture;
    private Timestamp createdAt;
}