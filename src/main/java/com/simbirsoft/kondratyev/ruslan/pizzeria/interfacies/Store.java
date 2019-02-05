package com.simbirsoft.kondratyev.ruslan.pizzeria.interfacies;

import com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs;

import java.util.Collection;
import java.util.Map;


public interface Store<Ingredient>{
    Wrongs getIngredient(final Ingredient type, final Integer quantity);
    void commitStore(Map<Ingredient,Integer> ingrediens);
    Collection<Ingredient> getAllIngredients();
    Integer getQuantity(final Ingredient type);
}