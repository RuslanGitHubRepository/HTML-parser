package com.simbirsoft.kondratyev.ruslan.pizzeria.controllers;

import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Ingredient;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Pizza;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs;
import com.simbirsoft.kondratyev.ruslan.pizzeria.service.impl.Kitchen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs.WRONG_INPUT;

@RestController
@RequestMapping("/private/kithen")
public class KithenController {

    @Autowired
    private Kitchen kithenService;

    @PutMapping("/replenishmentRecipe/{countToAdd}")
    public  HttpStatus addToRecipe(@PathVariable(required=true) int countToAdd, @RequestBody Ingredient ingredient) {
        Wrongs wrong = kithenService.addToRecipe(ingredient, countToAdd);
        if (wrong == WRONG_INPUT) {
            return HttpStatus.CONFLICT;
        }
        return HttpStatus.OK;
    }

    @GetMapping(value = "/readyRecipe/")
    public Pizza readyRecipe() {
        return kithenService.getPizza();
    }
}