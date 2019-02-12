package com.simbirsoft.kondratyev.ruslan.pizzeria;

import com.simbirsoft.kondratyev.ruslan.pizzeria.controllers.Controller;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.MakerException;
import com.simbirsoft.kondratyev.ruslan.pizzeria.repository.StoreHouse;
import com.simbirsoft.kondratyev.ruslan.pizzeria.repository.Kitchen;
import com.simbirsoft.kondratyev.ruslan.pizzeria.views.Dialog;


public class Main {
    private Main(){}
    public static void main(String[] args) throws Exception {
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
