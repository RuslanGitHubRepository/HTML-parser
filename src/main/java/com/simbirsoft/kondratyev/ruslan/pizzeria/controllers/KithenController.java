package com.simbirsoft.kondratyev.ruslan.pizzeria.controllers;

import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Ingredient;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Pizza;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs;
import com.simbirsoft.kondratyev.ruslan.pizzeria.repository.Kitchen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import static com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs.WRONG_INPUT;

@RestController
@RequestMapping("/private/kithen")
public class KithenController {
    private final String addToRecipe = "KithenController.addToRecipe ";
    private final String readyRecipe = "KithenController.readyRecipe ";

    @Autowired
    Kitchen kithenService;
    @Autowired
    private Logger log;

    @PutMapping("/replenishmentRecipe/{countToAdd}")
    public  HttpStatus addToRecipe(@PathVariable(required=true) int countToAdd, @RequestBody Ingredient ingredient) {

        log.info(addToRecipe + Calendar.getInstance().toString());

        Wrongs wrong = kithenService.addToRecipe(ingredient, countToAdd);
        if (wrong == WRONG_INPUT) {
            return HttpStatus.CONFLICT;
        }
        return HttpStatus.OK;
    }
    @GetMapping(value = "/readyRecipe/")
    public Pizza readyRecipe() {
        log.info(readyRecipe + Calendar.getInstance().toString());
        return kithenService.getPizza();
    }
}