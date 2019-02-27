package com.simbirsoft.kondratyev.ruslan.pizzeria.models;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
public class Pizza implements Serializable {
    private List<Pair<Integer,Ingredient>> ingredients = new ArrayList<>();
    private Integer sizePizza = 0;
    public Pizza(){}

    public void setIngredient(Integer count, Ingredient ingredient){
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

    public void setSizePizza(Integer sizePizza) {
        this.sizePizza = sizePizza;
    }
}
