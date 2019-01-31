package com.simbirsoft.kondratyev.ruslan.pizzeria.interfacies;

import com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs;

import java.util.Collection;


public interface Store<Ingredient>{
    Wrongs getIngredient(final Ingredient type, final Integer quantity) throws Exception;
    void restoreStore()throws Exception;
    void commitStore();
    Collection<Ingredient> getAllIngredients() throws Exception;
    Integer getQuantity(final Ingredient type) throws Exception ;
}