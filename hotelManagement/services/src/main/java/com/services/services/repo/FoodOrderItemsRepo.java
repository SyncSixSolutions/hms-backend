package com.services.services.repo;

import com.services.services.model.food.FoodOrderItemsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodOrderItemsRepo extends JpaRepository <FoodOrderItemsModel, Integer> {

}
