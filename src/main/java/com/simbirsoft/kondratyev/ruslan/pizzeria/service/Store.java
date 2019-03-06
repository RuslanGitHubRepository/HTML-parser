package com.simbirsoft.kondratyev.ruslan.pizzeria.service;

import com.simbirsoft.kondratyev.ruslan.pizzeria.dto.IngredientDto;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Pair;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs;

import java.util.List;


public interface Store<Ingredient>{
    Wrongs getIngredient(final Ingredient type, final Integer quantity);
    void commitStore(List<Pair<String,Integer>> ingrediens);
    List<IngredientDto> getAllIngredients();
    Integer getQuantity(final Ingredient type);
}