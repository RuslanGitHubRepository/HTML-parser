package com.simbirsoft.kondratyev.ruslan.pizzeria.service;

import static com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs.*;

import com.simbirsoft.kondratyev.ruslan.pizzeria.interfacies.Kitchens;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Ingredient;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs;
import com.simbirsoft.kondratyev.ruslan.pizzeria.views.Dialog;

import java.util.*;

public class Kitchen extends Kitchens<Ingredient> {
    private Map<Ingredient,Integer> recipe = new HashMap<>();
    private Integer sizePizza = 0;
    private Integer currentPortion = 0;
    private Integer typeOfPizza = 0;

    public static boolean readinessFlag = false;

    public Kitchen(Integer maxIngredient,Integer maxPizza) {
        maxPortionIngredient = maxIngredient;
        maxPortionPizza = maxPizza;
    }

    public Wrongs addToRecipe(Ingredient ingredient, Integer countToAdd) {
        if (countToAdd == Dialog.ABORT){
            return WRONG_WASHOUT;
        }
        if (countToAdd > maxPortionIngredient){
            return WRONG_INPUT;
        }
        if ((countToAdd + currentPortion) > maxPortionPizza){
            return WRONG_FORMATION;
        }
        if (countToAdd == 0){
            return WRONG_NONE;
        }
        recipe.put(ingredient,countToAdd);
        currentPortion += countToAdd;
        if (currentPortion.equals(maxPortionPizza)){
            readinessFlag = true;
        }
        return WRONG_NONE;
    }
    public void restartKitchen() {
        sizePizza = 0;
        currentPortion = 0;
        readinessFlag = false;
        recipe.clear();
    }
    public Wrongs setSizePizza(Integer sizePizza) {
        if (sizePizza == Dialog.ABORT){
            return WRONG_WASHOUT;
        }
        this.sizePizza = sizePizza;
        return WRONG_NONE;
    }
    public Collection<String> getPizza() {
        List<String> pizza = new ArrayList<>();
        pizza.add("Размер пиццы: " + sizePizza);
        for (Map.Entry<Ingredient,Integer> pair:recipe.entrySet()) {
            pizza.add(pair.getKey().getName() + "->" + pair.getValue() + " пр.");
        }
        readinessFlag = true;
        typeOfPizza++;
        return pizza;
    }
}
