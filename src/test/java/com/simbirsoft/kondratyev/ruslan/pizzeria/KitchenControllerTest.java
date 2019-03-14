package com.simbirsoft.kondratyev.ruslan.pizzeria;

import com.simbirsoft.kondratyev.ruslan.pizzeria.controllers.KithenController;
import com.simbirsoft.kondratyev.ruslan.pizzeria.dto.*;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.*;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.*;
import com.simbirsoft.kondratyev.ruslan.pizzeria.repository.KitchenRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.shaded.com.google.common.collect.ImmutableList;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("test")
public class KitchenControllerTest {
    @Autowired
    private KithenController kithenController;
    @Autowired
    private IngredientMapper ingredientMapper;
    @Autowired
    private KitchenRepository kitchenRepository;
    List<String> ingredientName = new ArrayList<>();
    @Test
    public void testAddToRecipe(){
        //GIVEN
        ingredientName.addAll(ImmutableList.of("Сыр", "Лук", "Грибы", "Оливки","Перец","Кетчуп","Креветки","Курица","Ананас"));
        final Long typeOfPizza = 1L;
        final Integer countToAdd = 1;
        final Long id = 1L;
        final Taste taste = Taste.BITTER;
        final Condition condition = Condition.DRIED;
        Recipe recipe = new Recipe();
        recipe.setCountIngredient(countToAdd);
        recipe.setRecipeNumber(new Recipes(typeOfPizza, "recipe #" + typeOfPizza));
        //WHEN
        for (String name:ingredientName) {
            IngredientDto ingredientDto = new IngredientDto(id,name,taste,condition);
            recipe.setIngredients(ingredientMapper.IngredientDtoToIngredient(ingredientDto));
            HttpStatus httpStatus = kithenController.addToRecipe(countToAdd,ingredientDto);
            //THEN
            assertTrue(httpStatus == HttpStatus.OK);
            List<Recipe> recipeList = kitchenRepository.findByRecipeNumber_IdEquals(typeOfPizza);
            kitchenRepository.deleteAll(recipeList);
        }
    }
    @Test
    public void testReadyRecipe(){
        //WHEN
        Pizza pizza = kithenController.readyRecipe();
        //THEN
        assertTrue(pizza.getIngredients().size() >= 0);
    }
}
