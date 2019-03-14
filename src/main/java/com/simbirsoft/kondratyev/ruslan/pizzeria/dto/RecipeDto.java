package com.simbirsoft.kondratyev.ruslan.pizzeria.dto;

import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDto {
    private IngredientDto ingredients;
    private Integer countIngredient;
}
