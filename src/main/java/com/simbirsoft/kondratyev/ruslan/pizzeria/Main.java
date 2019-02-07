package com.simbirsoft.kondratyev.ruslan.pizzeria;

import com.simbirsoft.kondratyev.ruslan.pizzeria.controllers.Controller;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.ConnectionPropertyFile;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.ConnectionSingltone;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.MakerException;
import com.simbirsoft.kondratyev.ruslan.pizzeria.repository.StoreHouse;
import com.simbirsoft.kondratyev.ruslan.pizzeria.repository.Kitchen;
import com.simbirsoft.kondratyev.ruslan.pizzeria.views.Dialog;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;


public class Main {
    private Main(){}
    public static void main(String[] args) throws Exception {
        String[] names = {"Сыр", "Лук", "Грибы", "Оливки", "Перец", "Керчуп", "Креветки", "Курица", "Ананас"};
        Integer[] counts = {3, 10, 18, 15, 16, 8, 6, 14, 19};
        ConnectionSingltone.initialisationObject(new ConnectionPropertyFile("configserver.properties"));
        try {
            StoreHouse storeHouse = new StoreHouse();
            Kitchen kitchen = new Kitchen(2,10);
            Dialog dialog = new Dialog();
            Controller controller = new Controller(storeHouse, dialog, kitchen);
            controller.exec();

        }catch(MakerException err){
            err.printStackTrace();
        }
    }
}
