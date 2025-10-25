package com.java10.MagicFridgeAI.controller;


import com.java10.MagicFridgeAI.model.FoodItem;
import com.java10.MagicFridgeAI.service.ChatGPTService;
import com.java10.MagicFridgeAI.service.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class RecipeController {


    @Autowired
    private ChatGPTService cgpts;

    @Autowired
    private FoodItemService foodItemService;


    @GetMapping("/generate")
    public Mono<ResponseEntity<String>> generateRecipe() {
        List<FoodItem> foodItemList = foodItemService.getAll();
        return cgpts.generateRecipe(foodItemList).map
                (recipe -> ResponseEntity.ok(recipe)).
                defaultIfEmpty(ResponseEntity.notFound().build());



    }

}
