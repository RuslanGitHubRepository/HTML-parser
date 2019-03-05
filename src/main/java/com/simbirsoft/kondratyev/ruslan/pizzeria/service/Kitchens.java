package com.simbirsoft.kondratyev.ruslan.pizzeria.service;

import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Pizza;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs;

import java.util.Collection;

public interface Kitchens<Ingredient> {
    Wrongs addToRecipe(Ingredient ingredient, Integer countToAdd);
    Pizza getPizza();
}
