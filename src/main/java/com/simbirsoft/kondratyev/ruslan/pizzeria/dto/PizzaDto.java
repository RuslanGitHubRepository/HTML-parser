package com.simbirsoft.kondratyev.ruslan.pizzeria.dto;

import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Ingredient;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Pair;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

@Setter
public class PizzaDto implements Serializable {
    private List<Pair<Integer, Ingredient>> ingredients = new ArrayList<>();
    private Integer sizePizza = 0;
    private final String insertIngredient = "Pizza.insertIngredient ";

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
