package com.foodapp.controller;

import com.foodapp.model.Cart;
import com.foodapp.model.FoodItem;
import com.foodapp.model.User;
import com.foodapp.repository.CartRepository;
import com.foodapp.repository.FoodItemRepository;
import com.foodapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/cart")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private FoodItemRepository foodItemRepository;

    @Autowired
    private UserRepository userRepository;

    // 🟢 ADD TO CART
    @PostMapping("/{foodItemId}")
    public Cart addToCart(
            @PathVariable Long foodItemId,
            Authentication authentication) {

        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        FoodItem foodItem = foodItemRepository.findById(foodItemId)
                .orElseThrow(() -> new RuntimeException("Food item not found"));

        // if item already exists → increase quantity
        return cartRepository.findByUserAndFoodItemId(user, foodItemId)
                .map(cart -> {
                    cart.setQuantity(cart.getQuantity() + 1);
                    return cartRepository.save(cart);
                })
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    cart.setFoodItem(foodItem);
                    cart.setQuantity(1);
                    return cartRepository.save(cart);
                });
    }

    // 🟢 VIEW CART
    @GetMapping
    public List<Cart> viewCart(Authentication authentication) {

        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return cartRepository.findByUser(user);
    }

    // 🔴 REMOVE ITEM
    @DeleteMapping("/{cartId}")
    public String removeFromCart(@PathVariable Long cartId) {
        cartRepository.deleteById(cartId);
        return "Item removed from cart";
    }
}
