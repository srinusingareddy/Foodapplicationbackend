package com.foodapp.controller;

import com.foodapp.model.Restaurant;
import com.foodapp.model.FoodItem;

import com.foodapp.repository.RestaurantRepository;
import com.foodapp.repository.FoodItemRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private FoodItemRepository foodItemRepository;

    // ================= RESTAURANT APIs =================

    @PostMapping("/restaurants")
    public Restaurant addRestaurant(@RequestBody Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }	

    @PutMapping("/restaurants/{id}")
    public Restaurant updateRestaurant(
            @PathVariable Long id,
            @RequestBody Restaurant restaurant) {

        Restaurant existing = restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + id));

        existing.setName(restaurant.getName());
        existing.setAddress(restaurant.getAddress());

        return restaurantRepository.save(existing);
    }

    @DeleteMapping("/restaurants/{id}")
    public String deleteRestaurant(@PathVariable Long id) {
        restaurantRepository.deleteById(id);
        return "Restaurant deleted successfully";
    }

    @GetMapping("/restaurants")
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }
    @GetMapping("/restaurants/{id}")
    public Restaurant getRestaurantById(@PathVariable Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
    }

    @GetMapping("/fooditems/{id}")
    public FoodItem getFoodItemById(@PathVariable Long id) {
        return foodItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food item not found"));
    }

    @GetMapping("/restaurants/{restaurantId}/fooditems")
    public List<FoodItem> getFoodItemsByRestaurant(@PathVariable Long restaurantId) {
        return foodItemRepository.findByRestaurantId(restaurantId);
    }


    // ================= FOOD ITEM APIs =================
    

    @PostMapping("/restaurants/{restaurantId}/fooditems")
    public FoodItem addFoodItem(
            @PathVariable Long restaurantId,
            @RequestBody FoodItem foodItem) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() ->
                        new RuntimeException("Restaurant not found with id: " + restaurantId));

        foodItem.setId(null); // 🔐 prevent override
        foodItem.setRestaurant(restaurant);

        return foodItemRepository.save(foodItem);
    }

    @PutMapping("/fooditems/{id}")
    public FoodItem updateFoodItem(
            @PathVariable Long id,
            @RequestBody FoodItem foodItem) {

        FoodItem existing = foodItemRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Food item not found with id: " + id));

        existing.setName(foodItem.getName());
        existing.setPrice(foodItem.getPrice());

        return foodItemRepository.save(existing);
    }

    @DeleteMapping("/fooditems/{id}")
    public String deleteFoodItem(@PathVariable Long id) {
        foodItemRepository.deleteById(id);
        return "Food item deleted successfully";
    }
    
   

}
