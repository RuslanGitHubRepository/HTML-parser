package com.simbirsoft.kondratyev.ruslan.pizzeria.repository;


import com.simbirsoft.kondratyev.ruslan.pizzeria.interfacies.KitchenRepository;
import com.simbirsoft.kondratyev.ruslan.pizzeria.interfacies.Kitchens;
import com.simbirsoft.kondratyev.ruslan.pizzeria.interfacies.StorageRepository;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Ingredient;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Pizza;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Recipe;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Recipes;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.*;

import static com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs.*;
import static com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs.WRONG_FORMATION;

@Transactional
public class Kitchen implements Kitchens<Ingredient> {

    @Autowired
    private KitchenRepository kitchenRepository;

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

    public Wrongs addToRecipe(Ingredient ingredient, Integer countToAdd) {

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
        recipe.setRecipeNumber(new Recipes("recipe #" + Kitchen.typeOfPizza, Kitchen.typeOfPizza));
        recipe.setIngredients(ingredient);

        kitchenRepository.save(recipe);

        currentPortion += countToAdd;
        if (currentPortion.equals(maxPortionPizza)){
            readinessFlag = true;
        }
        return WRONG_NONE;
    }

    public Wrongs setSizePizza(Integer sizePizza) {
        this.sizePizza = sizePizza;
        return WRONG_NONE;
    }

    public Pizza getPizza() {
        Pizza pizza = new Pizza();
        pizza.setSizePizza(sizePizza);
        List<Recipe> recipes = kitchenRepository.findByRecipeNumber_IdEquals(typeOfPizza);
        for(Recipe recipe: recipes){
            pizza.insertIngredient(recipe.getCountIngredient(), recipe.getIngredients());
        }
        readinessFlag = true;
        /*typeOfPizza;*/
        return pizza;
    }

    public void restartKitchen() {
        sizePizza = 0;
        currentPortion = 0;
        readinessFlag = false;
    }
}
