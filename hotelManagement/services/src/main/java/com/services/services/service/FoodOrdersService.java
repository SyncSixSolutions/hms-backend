package com.services.services.service;

import com.services.services.dto.FoodOrderItemsDTO;
import com.services.services.dto.FoodOrdersDTO;
import com.services.services.model.food.FoodOrderItemsModel;
import com.services.services.model.food.FoodOrdersModel;
import com.services.services.repo.FoodOrderItemsRepo;
import com.services.services.repo.FoodOrdersRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodOrdersService {

    @Autowired
    private FoodOrdersRepo foodOrdersRepo;

    @Autowired
    private FoodOrderItemsRepo foodOrderItemsRepo;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Create a new food order for a user
     */
    @Transactional
    public FoodOrdersDTO createOrder(FoodOrdersDTO orderDTO) {
        // Create and save the order
        FoodOrdersModel orderModel = new FoodOrdersModel();
        orderModel.setUserId(orderDTO.getUserId());
        orderModel.setOrderTime(orderDTO.getOrderTime() != null ? orderDTO.getOrderTime() : LocalDateTime.now());
        orderModel.setRoomId(orderDTO.getRoomId());
        orderModel.setNotes(orderDTO.getNotes());
        orderModel.setTotalPrice(orderDTO.getTotalPrice());

        // Save the order first to get the ID
        FoodOrdersModel savedOrder = foodOrdersRepo.save(orderModel);

        // Create and save order items
        List<FoodOrderItemsModel> itemModels = new ArrayList<>();
        for (FoodOrderItemsDTO itemDTO : orderDTO.getItems()) {
            FoodOrderItemsModel itemModel = new FoodOrderItemsModel();
            itemModel.setOrder(savedOrder);
            itemModel.setFoodId(itemDTO.getFoodNumber());
            itemModel.setQuantity(itemDTO.getQuantity());
            itemModel.setPrice(itemDTO.getPrice().doubleValue());
            itemModel.setDeliveryTime(orderDTO.getOrderTime() != null ? orderDTO.getOrderTime() : LocalDateTime.now());
            itemModels.add(itemModel);
        }

        List<FoodOrderItemsModel> savedItems = foodOrderItemsRepo.saveAll(itemModels);

        // Manually convert to DTO instead of using ModelMapper for collections
        FoodOrdersDTO resultDTO = new FoodOrdersDTO();
        resultDTO.setOrderId(savedOrder.getOrderId());
        resultDTO.setUserId(savedOrder.getUserId());
        resultDTO.setOrderTime(savedOrder.getOrderTime());
        resultDTO.setRoomId(savedOrder.getRoomId());
        resultDTO.setNotes(savedOrder.getNotes());
        resultDTO.setTotalPrice(savedOrder.getTotalPrice());

        // Convert items manually
        List<FoodOrderItemsDTO> itemDTOs = savedItems.stream()
                .map(item -> {
                    FoodOrderItemsDTO itemDTO = new FoodOrderItemsDTO();
                    itemDTO.setId(item.getOrderItemId());
                    itemDTO.setOrderId(savedOrder.getOrderId());
                    itemDTO.setFoodNumber(item.getFoodId());
                    itemDTO.setQuantity(item.getQuantity());
                    itemDTO.setPrice(BigDecimal.valueOf(item.getPrice()));
                    itemDTO.setSubtotal(BigDecimal.valueOf(item.getTotalPrice()));
                    return itemDTO;
                })
                .collect(Collectors.toList());

        resultDTO.setItems(itemDTOs);

        return resultDTO;
    }

    /**
     * Get all food orders for a specific user
     */
    public List<FoodOrdersDTO> getFoodOrdersByUserId(Integer userId) {
        List<FoodOrdersModel> orders = foodOrdersRepo.findByUserId(userId);
        return orders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get a specific food order by ID
     */
    public FoodOrdersDTO getFoodOrderById(Integer orderId) {
        FoodOrdersModel order = foodOrdersRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        return convertToDTO(order);
    }

    /**
     * Helper method to manually convert model to DTO
     */
    private FoodOrdersDTO convertToDTO(FoodOrdersModel order) {
        FoodOrdersDTO dto = new FoodOrdersDTO();
        dto.setOrderId(order.getOrderId());
        dto.setUserId(order.getUserId());
        dto.setOrderTime(order.getOrderTime());
        dto.setRoomId(order.getRoomId());
        dto.setNotes(order.getNotes());
        dto.setTotalPrice(order.getTotalPrice());

        // Convert items manually
        List<FoodOrderItemsDTO> itemDTOs = order.getItems().stream()
                .map(item -> {
                    FoodOrderItemsDTO itemDTO = new FoodOrderItemsDTO();
                    itemDTO.setId(item.getOrderItemId());
                    itemDTO.setOrderId(order.getOrderId());
                    itemDTO.setFoodNumber(item.getFoodId());
                    itemDTO.setQuantity(item.getQuantity());
                    itemDTO.setPrice(BigDecimal.valueOf(item.getPrice()));
                    itemDTO.setSubtotal(BigDecimal.valueOf(item.getTotalPrice()));
                    return itemDTO;
                })
                .collect(Collectors.toList());

        dto.setItems(itemDTOs);

        return dto;
    }
}
