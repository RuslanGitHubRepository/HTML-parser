package com.simbirsoft.kondratyev.ruslan.pizzeria;

import com.simbirsoft.kondratyev.ruslan.pizzeria.controllers.Controller;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Ingredient;
import com.simbirsoft.kondratyev.ruslan.pizzeria.repository.StoreHouse_DB;
import com.simbirsoft.kondratyev.ruslan.pizzeria.service.Kitchen;
import com.simbirsoft.kondratyev.ruslan.pizzeria.repository.StoreHouse;
import com.simbirsoft.kondratyev.ruslan.pizzeria.service.Kitchen_DB;
import com.simbirsoft.kondratyev.ruslan.pizzeria.views.Dialog;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    private Main(){}
    public static void main(String[] args){
        String[] names = {"Сыр","Лук","Грибы","Оливки","Перец","Керчуп","Креветки","Курица","Ананас"};
        Integer[] counts = {3,10,18,15,16,8,6,14,19};
        try {

            StoreHouse_DB storeHouse = new StoreHouse_DB(
                    new ArrayList<>(Arrays.asList(names)),
                    new ArrayList<>(Arrays.asList(counts)));

            Kitchen_DB kitchen = new Kitchen_DB(new ArrayList<>(Arrays.asList(names)),2,10);
            Dialog dialog = new Dialog();
            Controller controller = new Controller(storeHouse,dialog,kitchen);
            controller.exec();

        } catch(Exception err){
            System.out.println(err.getMessage());
        }
    }
}
