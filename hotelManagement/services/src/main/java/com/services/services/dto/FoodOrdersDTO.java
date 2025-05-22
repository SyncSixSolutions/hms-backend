package com.services.services.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodOrdersDTO {
    private Integer orderId;
    private Integer userId;
    private LocalDateTime orderTime;
    private Integer roomId;
    private String notes;
    private Double totalPrice;
    private List<FoodOrderItemsDTO> items;
}
