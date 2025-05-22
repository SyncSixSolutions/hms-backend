package com.services.services.model.food;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "food")
public class FoodModel { //this is the food model
    @Id
    private int foodNumber;
    private String foodName;
    private String availableTimes;
    private String foodNature;
    private BigDecimal price;
    private String description;
    private String foodPicture;

    @CreationTimestamp
    private Timestamp createdAt;
}