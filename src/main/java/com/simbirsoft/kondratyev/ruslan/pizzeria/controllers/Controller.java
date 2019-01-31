package com.simbirsoft.kondratyev.ruslan.pizzeria.controllers;

import com.simbirsoft.kondratyev.ruslan.pizzeria.interfacies.Controllers;
import com.simbirsoft.kondratyev.ruslan.pizzeria.interfacies.Kitchens;
import com.simbirsoft.kondratyev.ruslan.pizzeria.interfacies.Store;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Ingredient;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Pair;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs;
import com.simbirsoft.kondratyev.ruslan.pizzeria.service.Kitchen;
import com.simbirsoft.kondratyev.ruslan.pizzeria.service.Kitchen_DB;
import com.simbirsoft.kondratyev.ruslan.pizzeria.views.Dialog;

import java.util.*;

import static com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Suggest.INGREDIENT;
import static com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Suggest.SIZE;
import static com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs.*;

public class Controller implements Controllers<Ingredient> {
    private Store<Ingredient> storeHouse;
    private Dialog dialog;
    private Kitchens<Ingredient> kitchen;
    private Wrongs globalWrongs;

    private void getSizePizza() {
        dialog.wish();
        globalWrongs = kitchen.setSizePizza(dialog.suggest(SIZE, null));
    }
    private void creatRecipe(final Collection<Ingredient> ingredients) throws Exception{
        Integer countIngredientStore = 0;
        Integer countIngredientUser = 0;

        for (Ingredient ingredient : ingredients) {
            countIngredientStore = storeHouse.getQuantity(ingredient);
            if (countIngredientStore == 0) {
                continue;
            }
            while (true) {
                countIngredientUser = dialog.suggest(INGREDIENT, new Pair<>(ingredient, countIngredientStore));

                globalWrongs = kitchen.addToRecipe(ingredient, countIngredientUser);

                if (globalWrongs == WRONG_NONE){
                  globalWrongs = storeHouse.getIngredient(ingredient, countIngredientUser);
                }

                if (globalWrongs == WRONG_INPUT || globalWrongs == WRONG_FORMATION) {
                    dialog.wrongMessage(globalWrongs);
                }

                if (globalWrongs == WRONG_NONE || globalWrongs == WRONG_WASHOUT){
                    break;
                }
            }
            if (globalWrongs == WRONG_WASHOUT || Kitchens.readinessFlag) {
                break;
            }
        }
    }

    public Controller(final Store<Ingredient> storeHouse, final Dialog dialog, final Kitchens<Ingredient> kitchen){
        this.storeHouse = storeHouse;
        this.dialog = dialog;
        this.kitchen = kitchen;
    }
    public void exec() throws Exception{
        boolean status = dialog.welcome();

        Collection<Ingredient> ingredients = storeHouse.getAllIngredients();

        while (status) {
            kitchen.restartKitchen();
            storeHouse.commitStore();
            while (!Kitchen_DB.readinessFlag) {
                getSizePizza();

                if (globalWrongs == WRONG_WASHOUT){
                    break;
                }
                creatRecipe(ingredients);

                if (globalWrongs == WRONG_WASHOUT) {
                    storeHouse.restoreStore();
                    break;
                }

                dialog.orderFormed();
                dialog.issueResult(kitchen.getPizza());

            }
            status = dialog.repeat();
        }
        dialog.goodbuy();
    }
}
