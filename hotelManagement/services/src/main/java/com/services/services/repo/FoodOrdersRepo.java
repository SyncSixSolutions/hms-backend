package com.services.services.repo;

import com.services.services.model.food.FoodOrdersModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodOrdersRepo extends JpaRepository<FoodOrdersModel, Integer> {

}
