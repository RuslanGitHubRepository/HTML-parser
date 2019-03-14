package com.simbirsoft.kondratyev.ruslan.pizzeria.dto;

import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Ingredient;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {IngredientMapper.class})
public interface RecipeMapper {
    RecipeDto RecipeToRecipeDto(Recipe recipe);
    Recipe RecipeDtoToRecipe(RecipeDto recipeDto);
}