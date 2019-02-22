package com.simbirsoft.kondratyev.ruslan.pizzeria.interfacies;

import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Pizza;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs;

import java.util.Collection;

public interface Kitchens<Ingredient> {
    Wrongs addToRecipe(Ingredient ingredient, Integer countToAdd);
    void restartKitchen();
    Wrongs setSizePizza(Integer sizePizza);
    Pizza getPizza();
}
