package com.services.services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodOrderItemsDTO {
    private Integer id;
    private Integer orderId;
    private Integer foodNumber;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal subtotal;
}
