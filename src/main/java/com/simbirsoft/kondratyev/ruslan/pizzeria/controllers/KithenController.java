package com.simbirsoft.kondratyev.ruslan.pizzeria.controllers;

import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Ingredient;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Pizza;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs;
import com.simbirsoft.kondratyev.ruslan.pizzeria.repository.Kitchen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.List;

import static com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs.WRONG_INPUT;

@RestController
@RequestMapping("kithen")
public class KithenController {
    @Autowired
    Kitchen kithenService;

    @PutMapping("/replenishmentRecipe/")
    public  HttpStatus addToRecipe(@RequestParam(value="param", required=true) int countToAdd, @RequestBody Ingredient ingredient) {
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