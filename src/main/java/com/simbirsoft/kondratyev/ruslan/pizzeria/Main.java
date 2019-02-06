package com.simbirsoft.kondratyev.ruslan.pizzeria;

import com.simbirsoft.kondratyev.ruslan.pizzeria.controllers.Controller;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.MakerException;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.PropertySingltone;
import com.simbirsoft.kondratyev.ruslan.pizzeria.repository.StoreHouse;
import com.simbirsoft.kondratyev.ruslan.pizzeria.service.Kitchen;
import com.simbirsoft.kondratyev.ruslan.pizzeria.views.Dialog;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;


public class Main {
    private Main(){}
    public static void main(String[] args) throws Exception {
        String[] names = {"Сыр", "Лук", "Грибы", "Оливки", "Перец", "Керчуп", "Креветки", "Курица", "Ананас"};
        Integer[] counts = {3, 10, 18, 15, 16, 8, 6, 14, 19};
        File file = new File("./src/main/resources/configserver.properties");
        InputStream input = new FileInputStream(file);
        PropertySingltone.load(input);
        try {
            StoreHouse storeHouse = new StoreHouse(
                    new ArrayList<>(Arrays.asList(names)),
                    new ArrayList<>(Arrays.asList(counts)));

            Kitchen kitchen = new Kitchen(
                    new ArrayList<>(Arrays.asList(names)),
                    2,
                    10);
            Dialog dialog = new Dialog();
            Controller controller = new Controller(storeHouse, dialog, kitchen);
            controller.exec();

        }catch(MakerException err){
            err.printStackTrace();
        }
    }
}
