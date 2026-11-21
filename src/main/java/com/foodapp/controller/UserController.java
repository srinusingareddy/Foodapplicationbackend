package com.foodapp.controller;

import com.foodapp.model.FoodItem;
import com.foodapp.model.Restaurant;
import com.foodapp.repository.FoodItemRepository;
import com.foodapp.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private FoodItemRepository foodItemRepository;

    // VIEW RESTAURANTS
    @GetMapping("/restaurants")
    public List<Restaurant> viewRestaurants() {
        return restaurantRepository.findAll();
    }

    // VIEW ALL FOOD ITEMS
    @GetMapping("/fooditems")
    public List<FoodItem> getAllFoodItems() {
        return foodItemRepository.findAll();
    }

    // VIEW FOOD ITEMS BY RESTAURANT
    @GetMapping("/restaurants/{restaurantId}/fooditems")
    public List<FoodItem> getFoodItemsByRestaurant(
            @PathVariable Long restaurantId) {
        return foodItemRepository.findByRestaurantId(restaurantId);
    }
}
