package com.simbirsoft.kondratyev.ruslan.pizzeria.controllers;

import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Ingredient;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Pair;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs;
import com.simbirsoft.kondratyev.ruslan.pizzeria.service.Kitchen;
import com.simbirsoft.kondratyev.ruslan.pizzeria.repository.StoreHouse;
import com.simbirsoft.kondratyev.ruslan.pizzeria.views.Dialog;

import java.util.*;

import static com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Suggest.INGREDIENT;
import static com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Suggest.SIZE;
import static com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs.*;

public class Controller {
    public Controller(final StoreHouse storeHouse, final Dialog dialog, final Kitchen kitchen){
        this.storeHouse = storeHouse;
        this.dialog = dialog;
        this.kitchen = kitchen;
    }
    public void exec(){
        boolean status;

        Integer countIngredientStore,countIngredientUser = 0;// totalIngredients = 0;
        Collection<Ingredient> ingredients = storeHouse.getAllIngredients();
        //List<String> pizza = new ArrayList<>();

        status = dialog.welcome();
        while(status) {
            kitchen.restartKitchen();
            while(!Kitchen.readinessFlag) {
                dialog.wish();
                globalWrongs = kitchen.setSizePizza(dialog.suggest(SIZE, null));
                if (globalWrongs == WRONG_WASHOUT) break;
                for (Ingredient ingredient : ingredients) {
                    countIngredientStore = storeHouse.getQuantity(ingredient);
                    if (countIngredientStore == 0) continue;
                    while (true) {
                        countIngredientUser = dialog.suggest(INGREDIENT, new Pair<>(ingredient, countIngredientStore));
                        globalWrongs = storeHouse.getIngredient(ingredient,countIngredientUser);
                        if(globalWrongs == WRONG_NONE)
                            globalWrongs = kitchen.addToPecipe(ingredient, countIngredientUser, countIngredientStore);
                        if (globalWrongs == WRONG_WASHOUT) break;
                        else if (globalWrongs == WRONG_INPUT) dialog.wrongMessage(globalWrongs);
                        else if (globalWrongs == WRONG_FORMATION) dialog.wrongMessage(WRONG_FORMATION);
                        else break;
                    }
                    if (globalWrongs == WRONG_WASHOUT) break;

                    if (Kitchen.readinessFlag) break;
                }
                if (globalWrongs == WRONG_WASHOUT){
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
    private StoreHouse storeHouse;
    private Dialog dialog;
    private Kitchen kitchen;
    private Wrongs globalWrongs;
}
