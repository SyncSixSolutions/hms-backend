package com.services.services.model.food;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import java.util.List;

@Entity
@Table(name = "orders")
public class FoodOrdersModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    private Integer userId;
    private LocalDateTime orderTime = LocalDateTime.now();
    private Integer roomId;
    private String notes;
    private Double totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FoodOrderItemsModel> items;
}
