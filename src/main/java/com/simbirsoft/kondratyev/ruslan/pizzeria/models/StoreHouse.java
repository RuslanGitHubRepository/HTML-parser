package com.simbirsoft.kondratyev.ruslan.pizzeria.models;

import com.simbirsoft.kondratyev.ruslan.pizzeria.Ingredient;
import com.simbirsoft.kondratyev.ruslan.pizzeria.Pair;
import com.simbirsoft.kondratyev.ruslan.pizzeria.enums.Wrongs;
import com.simbirsoft.kondratyev.ruslan.pizzeria.views.Dialog;

import java.util.*;

import static com.simbirsoft.kondratyev.ruslan.pizzeria.enums.Wrongs.*;


final public class StoreHouse {

    public StoreHouse(final Collection<String> types, final Collection<Integer> quantity){
        storage = new HashMap<>();
        historyStorage = new HashMap<>();
        Iterator<Integer> quantityIter = quantity.iterator();
        for(String type : types) {
            storage.put(type, new Stack<>());
            historyStorage.put(type, new Stack<>());
            Integer g = quantityIter.next();
            for (int i = 0; i < g; i++) {
                storage.get(type).push(new Ingredient(type));
            }
        }
    }
    public final Wrongs getIngredient(final Ingredient type, final Integer quantity){
        if(quantity == 0) return WRONG_NONE;
        if(quantity == Dialog.ABORT) return WRONG_WASHOUT;
        if(storage.containsKey(type.getName())){
            Stack<Ingredient> stack = storage.get(type.getName());
            Stack<Ingredient> historyStack = historyStorage.get(type.getName());
            if(!stack.empty() && stack.size() >= quantity){
                for(int i = quantity; i > 0 ;i--){
                    historyStack.push(stack.pop());
                }
                return WRONG_NONE;
            }
        }
        return WRONG_INPUT;
    }
    public final void restoreStore(){
        for(Map.Entry<String,Stack<Ingredient>> pair : historyStorage.entrySet()){
            Stack<Ingredient> stack = storage.get(pair.getKey());
            for(int i=0;i<pair.getValue().size();i++)
                stack.push(pair.getValue().pop());
        }
    }
    public final Collection<Ingredient> getAllIngredients(){
        List<Ingredient> ingredients = new ArrayList<>();
        for(Map.Entry<String,Stack<Ingredient>> pair: storage.entrySet()){
            ingredients.add(pair.getValue().peek());
        }
        return ingredients;
    }
    public final Integer getQuantity(final Ingredient type){
        return storage.containsKey(type.getName())? storage.get(type.getName()).size(): 0;
    }
    public String toString() {
        String identification = new String();
        for (Map.Entry<String, Stack<Ingredient>> entry : storage.entrySet()) {
            identification += entry.getKey().toString();
            identification += "(" + entry.getValue().size() + "), ";
        }
        return identification;
    }

    private final Map<String, Stack<Ingredient>> storage;
    private final Map<String, Stack<Ingredient>> historyStorage;
}
