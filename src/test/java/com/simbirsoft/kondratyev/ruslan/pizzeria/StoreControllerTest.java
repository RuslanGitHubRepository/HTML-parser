package com.simbirsoft.kondratyev.ruslan.pizzeria;

import com.simbirsoft.kondratyev.ruslan.pizzeria.controllers.StoreController;
import com.simbirsoft.kondratyev.ruslan.pizzeria.dto.IngredientDto;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.shaded.com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StoreControllerTest {
    @Autowired
    private StoreController storeController;
    List<String> ingredientName = new ArrayList<>();
    List<Pair<String,Integer>> commitIngredient = new ArrayList<>();
    @Before
    public void initIngredients() {
        commitIngredient.addAll(ImmutableList.of(new Pair<>("Сыр",0),new Pair<>("Лук",0),new Pair<>("Грибы",0),new Pair<>("Оливки",0),new Pair<>("Перец",0),new Pair<>("Кетчуп",0)));
    }
    @Test
    @Profile("test")
    public void testGetIngredient(){
        //GIVEN
        ingredientName.addAll(ImmutableList.of("Сыр", "Лук", "Грибы", "Оливки","Перец","Кетчуп","Креветки","Курица","Ананас"));
        for(String name: ingredientName) {
            //WHEN
            HttpStatus httpStatus = storeController.getIngredient(name, 2);
            //THEN
            assertEquals(HttpStatus.OK, httpStatus);
        }
    }
    @Test
    @Profile("test")
    public void testGetIngredientNotFound(){
        //GIVEN
        String name = "Чеснок";
        //WHEN
        HttpStatus httpStatus = storeController.getIngredient(name, 2);
        //THEN
        assertEquals(HttpStatus.NO_CONTENT, httpStatus);
    }
    @Test
    public void testQuantity(){
        //GIVEN
        ingredientName.addAll(ImmutableList.of("Сыр", "Лук", "Грибы", "Оливки","Перец","Кетчуп","Креветки","Курица","Ананас"));
        //WHEN
        for(String name: ingredientName) {
            Integer count = storeController.getQuantity(name);
        //THEN
            assertTrue(count != 0);
        }
    }
    @Test
    @Profile("test")
    public void testQuantityNotFound(){
        //GIVEN
        String name = "Чеснок";
        //WHEN
        Integer count = storeController.getQuantity(name);
        //THEN
        assertFalse(count != 0);
    }
    @Test
    @Profile("test")
    public void testAllIngredient(){
        //WHEN
        List<IngredientDto>  dtoList = storeController.getAllIngredients();
        //THEN
        assertFalse(dtoList.size() == 0);
    }
    @Test
    @Profile("test")
    public void testCommitStore(){
        //WHEN
        HttpStatus httpStatus = storeController.commitStore(commitIngredient);
        //THEN
        assertTrue(httpStatus == HttpStatus.OK);
    }
}