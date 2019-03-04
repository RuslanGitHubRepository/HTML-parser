package com.simbirsoft.kondratyev.ruslan.pizzeria.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Ingredient;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Pair;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs;
import com.simbirsoft.kondratyev.ruslan.pizzeria.repository.StoreHouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;
import java.util.logging.Logger;

import static com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs.WRONG_NONE;

@RestController
@RequestMapping("/private/store")
public class StoreController {

    private final String getIngredient = "StoreController.getIngredient ";
    private final String getQuantity = "StoreController.getQuantity ";
    private final String getAllIngredients = "StoreController.getAllIngredients ";
    private final String commitStore = "StoreController.commitStore ";
    @Autowired
    StoreHouse storeService;

    @Autowired
    private Logger log;

    @GetMapping("/isFit/{ingredientName}")
    public HttpStatus getIngredient(@PathVariable String ingredientName, @RequestParam int quantity) {

        log.info(getIngredient + Calendar.getInstance().toString());

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
        log.info(getQuantity + Calendar.getInstance().toString());
        Ingredient ingredient = new Ingredient();
        ingredient.setName(ingredientName);
        Integer count = storeService.getQuantity(ingredient);
        return count;
    }
    @GetMapping
    public ArrayList<Ingredient> getAllIngredients() {
        log.info(getAllIngredients + Calendar.getInstance().toString());
        ArrayList<Ingredient> collection = new ArrayList<>();
        collection.addAll(storeService.getAllIngredients());
        return collection;
    }
    @PutMapping
    public HttpStatus commitStore(@RequestBody List<Pair<String,Integer>> ingrediens) {
        log.info(commitStore + Calendar.getInstance().toString());
        storeService.commitStore(ingrediens);
        return HttpStatus.OK;
    }
}