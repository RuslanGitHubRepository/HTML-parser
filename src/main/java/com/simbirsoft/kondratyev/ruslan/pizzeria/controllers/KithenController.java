package com.simbirsoft.kondratyev.ruslan.pizzeria.controllers;

import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Ingredient;
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
public class KithenController {
    @Autowired
    Kitchen kithenService;

    @RequestMapping(value = "/addToRecipe/{countToAdd}", method = RequestMethod.PUT)
    public ResponseEntity<Ingredient> addToRecipe(@PathVariable("countToAdd") int countToAdd, @RequestBody Ingredient ingredient) {
        Wrongs wrong = kithenService.addToRecipe(ingredient, countToAdd);
        if (wrong == WRONG_INPUT) {
            return new ResponseEntity<Ingredient>(ingredient, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Ingredient>(ingredient, HttpStatus.OK);
    }
    @RequestMapping(value = "/getPizza/", method = RequestMethod.GET)
    public ResponseEntity<Collection<String>> getPizza() {
        return new ResponseEntity<Collection<String>>(kithenService.getPizza(), HttpStatus.OK);
    }
}