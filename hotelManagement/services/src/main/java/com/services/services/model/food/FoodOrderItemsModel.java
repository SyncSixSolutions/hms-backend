package com.services.services.model.food;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodOrderItemsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderItemId;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private FoodOrdersModel order;

    @Column(name = "food_id")
    private Integer foodId;

    private Integer quantity;

    private Double price;

    private LocalDateTime deliveryTime;

    private Double totalPrice;

    @PrePersist
    @PreUpdate
    public void calculateTotal() {
        this.totalPrice = this.price * this.quantity;
    }
}
