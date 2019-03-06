package com.simbirsoft.kondratyev.ruslan.pizzeria.service.impl;


import com.simbirsoft.kondratyev.ruslan.pizzeria.dto.IngredientDto;
import com.simbirsoft.kondratyev.ruslan.pizzeria.dto.IngredientMapper;
import com.simbirsoft.kondratyev.ruslan.pizzeria.dto.PizzaDto;
import com.simbirsoft.kondratyev.ruslan.pizzeria.repository.KitchenRepository;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Ingredient;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Recipe;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Recipes;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs;
import com.simbirsoft.kondratyev.ruslan.pizzeria.service.Kitchens;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.*;

import static com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs.*;
import static com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs.WRONG_FORMATION;

@Transactional
public class Kitchen implements Kitchens<IngredientDto> {

    @Autowired
    private KitchenRepository kitchenRepository;
    @Autowired
    private IngredientMapper ingredientMapper;

    private Integer sizePizza = 0;
    public static Long typeOfPizza = 0L;
    private Integer currentPortion = 0;
    public static boolean readinessFlag = false;
    public static Integer maxPortionPizza = 0;
    public static Integer maxPortionIngredient = 0;

    public Kitchen(final Integer maxIngredient, final Integer maxPizza) {
        maxPortionIngredient = maxIngredient;
        maxPortionPizza = maxPizza;
        typeOfPizza = 1L;
    }

    public Wrongs addToRecipe(IngredientDto ingredientDto, Integer countToAdd) {

        if (countToAdd > maxPortionIngredient){
            return WRONG_INPUT;
        }
        if ((countToAdd + currentPortion) > maxPortionPizza){
            return WRONG_FORMATION;
        }
        if (countToAdd == 0){
            return WRONG_NONE;
        }
        Recipe recipe = new Recipe();
        recipe.setCountIngredient(countToAdd);
        recipe.setRecipeNumber(new Recipes(Kitchen.typeOfPizza, "recipe #" + Kitchen.typeOfPizza));
        recipe.setIngredients(ingredientMapper.IngredientDtoToIngredient(ingredientDto));

        kitchenRepository.save(recipe);

        currentPortion += countToAdd;
        if (currentPortion.equals(maxPortionPizza)){
            readinessFlag = true;
        }
        return WRONG_NONE;
    }

    public PizzaDto getPizza() {

        PizzaDto pizza = new PizzaDto();
        pizza.setSizePizza(sizePizza);
        List<Recipe> recipes = kitchenRepository.findByRecipeNumber_IdEquals(typeOfPizza);
        for(Recipe recipe: recipes){
            pizza.insertIngredient(recipe.getCountIngredient(), recipe.getIngredients());
        }
        readinessFlag = true;
        return pizza;
    }
}
