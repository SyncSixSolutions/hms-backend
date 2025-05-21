package com.services.services.model.food;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_items")
public class FoodOrderItemsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderItemId;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private FoodOrdersModel order;

    private Integer foodId;

    private Integer quantity;

    private Double price;

    private LocalDateTime deliveryTime;

    private Double totalPrice;

    @PrePersist
    @PreUpdate
    private void calculateTotal() {
        this.totalPrice = this.price * this.quantity;
    }

    // Getters and Setters
}
