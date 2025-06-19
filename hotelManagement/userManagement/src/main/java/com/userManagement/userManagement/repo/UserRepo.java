package com.userManagement.userManagement.repo;

import com.userManagement.userManagement.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserModel, Integer> {

    Optional<UserModel> findByEmailIgnoreCase(String email);

    @Query(value = "SELECT * FROM user_model WHERE user_role = 'customer'", nativeQuery = true)
    List<UserModel> findAllCustomers();

}