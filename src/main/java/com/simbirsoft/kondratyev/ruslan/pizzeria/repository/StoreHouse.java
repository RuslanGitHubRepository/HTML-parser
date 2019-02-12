package com.simbirsoft.kondratyev.ruslan.pizzeria.repository;

import com.simbirsoft.kondratyev.ruslan.pizzeria.interfacies.Store;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.DataExchange;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.HibernateDataBase.Ingredient;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.HibernateDataBase.Storage;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs;
import com.simbirsoft.kondratyev.ruslan.pizzeria.views.Dialog;

import java.util.*;

import static com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs.*;


public class StoreHouse  implements Store<Ingredient> {

    public Wrongs getIngredient(final Ingredient type, final Integer quantity) {
        if (quantity == 0) {
            return WRONG_NONE;
        }
        if (quantity == Dialog.ABORT) {
            return WRONG_WASHOUT;
        }
        Integer countIngredientDB = getQuantity(type);
        if ((countIngredientDB < quantity) || (countIngredientDB == 0)) {
            return WRONG_INPUT;
        }
        return WRONG_NONE;
    }

    public Integer getQuantity(final Ingredient type){
       return DataExchange.getRequestStorage(type).getTailsIngredient();
    }

    public Collection<Ingredient> getAllIngredients() {
        Collection<Ingredient> listIndredients = new LinkedList<>();
        List<Storage> listStorage = DataExchange.getRequestStorageAll();
        for(Storage storage:listStorage){
            ((LinkedList<Ingredient>) listIndredients).addLast(storage.getIngredients());
        }
        return listIndredients;
    }

    public void commitStore(Map<Ingredient,Integer> ingrediens) {
        if(ingrediens.size() == 0){
            return;
        }
        List<Storage> listStorage = new ArrayList<>();
        for(Map.Entry<Ingredient,Integer> entry: ingrediens.entrySet()){
            listStorage.add(new Storage(entry.getValue(),0.0, entry.getKey()));
        }
        DataExchange.updateStorage(listStorage);
    }
}