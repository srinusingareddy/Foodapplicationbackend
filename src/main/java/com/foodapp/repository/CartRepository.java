package com.foodapp.repository;

import com.foodapp.model.Cart;
import com.foodapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByUser(User user);

    Optional<Cart> findByUserAndFoodItemId(User user, Long foodItemId);
}
