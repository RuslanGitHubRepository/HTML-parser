package com.simbirsoft.kondratyev.ruslan.pizzeria.models;

import com.simbirsoft.kondratyev.ruslan.pizzeria.dto.RecipeDto;
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
@Getter
public class Pizza implements Serializable {
    private List<RecipeDto> ingredients = new ArrayList<>();
    private Integer sizePizza = 0;

    public void insertIngredient(RecipeDto recipeDto){
        ingredients.add(recipeDto);
    }

    @Override
    public String toString() {
        String pizzaDescription =  new String("Размер пиццы "+sizePizza+";\n");
        for (RecipeDto recipeDto:ingredients) {
            //pizzaDescription += pair.getSecond() + " -> " + pair.getFirst() + ";\n";
        }
        return pizzaDescription;
    }
}
