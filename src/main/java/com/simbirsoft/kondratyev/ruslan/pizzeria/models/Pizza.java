package com.simbirsoft.kondratyev.ruslan.pizzeria.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Setter
public class Pizza implements Serializable {
    private List<Pair<Integer,Ingredient>> ingredients = new ArrayList<>();
    private Integer sizePizza = 0;

    public void insertIngredient(Integer count, Ingredient ingredient){
        ingredients.add(new Pair<>(count,ingredient));
    }

    @Override
    public String toString() {
        String pizzaDescription =  new String("Размер пиццы "+sizePizza+";\n");
        for (Pair<Integer,Ingredient> pair:ingredients) {
            pizzaDescription += pair.getSecond() + " -> " + pair.getFirst() + ";\n";
        }
        return pizzaDescription;
    }
}
