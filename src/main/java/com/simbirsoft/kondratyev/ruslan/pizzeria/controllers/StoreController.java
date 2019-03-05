package com.simbirsoft.kondratyev.ruslan.pizzeria.controllers;

import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Ingredient;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Pair;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs;
import com.simbirsoft.kondratyev.ruslan.pizzeria.service.impl.StoreHouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.*;

import static com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs.WRONG_NONE;

@RestController
@RequestMapping("/private/store")
public class StoreController {
    @Autowired
    private StoreHouse storeService;

    @GetMapping("/isFit/{ingredientName}")
    public HttpStatus getIngredient(@PathVariable String ingredientName, @RequestParam int quantity) {

        Ingredient ingredient = new Ingredient();
        ingredient.setName(ingredientName);
        Wrongs wrong = storeService.getIngredient(ingredient, quantity);

        if (wrong != WRONG_NONE) {
            return HttpStatus.NO_CONTENT;
        }
        return HttpStatus.OK;
    }

    @GetMapping("/quantity/{ingredientName}")
    public Integer getQuantity(@PathVariable String ingredientName) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(ingredientName);
        Integer count = storeService.getQuantity(ingredient);
        return count;
    }

    @GetMapping
    public Ingredient getAllIngredients() {
        List<Ingredient> collection = new ArrayList<>();
        collection.addAll(storeService.getAllIngredients());
        return collection.get(0);
    }

    @PutMapping
    public HttpStatus commitStore(@RequestBody List<Pair<String,Integer>> ingrediens) {
        storeService.commitStore(ingrediens);
        return HttpStatus.OK;
    }
}