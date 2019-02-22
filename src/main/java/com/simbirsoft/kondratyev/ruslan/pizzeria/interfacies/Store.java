package com.simbirsoft.kondratyev.ruslan.pizzeria.interfacies;

import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Pair;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs;

import java.util.Collection;
import java.util.List;
import java.util.Map;


public interface Store<Ingredient>{
    Wrongs getIngredient(final Ingredient type, final Integer quantity);
    void commitStore(List<Pair<String,Integer>> ingrediens);
    Collection<Ingredient> getAllIngredients();
    Integer getQuantity(final Ingredient type);
}