package com.services.services.repo;

import com.services.services.model.food.FoodModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FoodRepo extends JpaRepository<FoodModel, Integer> {
    @Query("SELECT f FROM FoodModel f WHERE f.availableTimes LIKE %:time%")
    List<FoodModel> findByAvailableTimeContaining(String time);
}