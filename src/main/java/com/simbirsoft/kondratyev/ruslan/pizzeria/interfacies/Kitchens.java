package com.simbirsoft.kondratyev.ruslan.pizzeria.interfacies;

import com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs;

import java.util.Collection;

public abstract class Kitchens<Ingredient> {
    public static boolean readinessFlag = false;
    public static Integer maxPortionPizza = 0;
    public static Integer maxPortionIngredient = 0;

    abstract public Wrongs addToRecipe(Ingredient ingredient, Integer countToAdd) throws Exception;
    abstract public void restartKitchen() throws Exception;
    abstract public Wrongs setSizePizza(Integer sizePizza);
    abstract public Collection<String> getPizza() throws Exception;
}
