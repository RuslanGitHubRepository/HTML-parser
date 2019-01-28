package com.simbirsoft.kondratyev.ruslan.pizzeria;

import com.simbirsoft.kondratyev.ruslan.pizzeria.controllers.Controller;
import com.simbirsoft.kondratyev.ruslan.pizzeria.service.Kitchen;
import com.simbirsoft.kondratyev.ruslan.pizzeria.repository.StoreHouse;
import com.simbirsoft.kondratyev.ruslan.pizzeria.views.Dialog;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    private Main(){}
    public static void main(String[] args){
        String[] names = {"Сыр","Лук","Грибы","Оливки","Перец","Керчуп","Креветки","Курица","Ананас"};
        Integer[] counts = {3,10,18,15,16,8,6,14,19};
        Dialog dialog = new Dialog();
        Kitchen kitchen = new Kitchen(2,10);
        StoreHouse storeHouse = new StoreHouse(
                new ArrayList<String>(Arrays.asList(names)),
                new ArrayList<Integer>(Arrays.asList(counts))
        );
        Controller controller = new Controller(storeHouse,dialog,kitchen);
        controller.exec();
    }
}
