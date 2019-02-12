package com.simbirsoft.kondratyev.ruslan.pizzeria.views;

import com.simbirsoft.kondratyev.ruslan.pizzeria.models.HibernateDataBase.Ingredient;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.*;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Pair;
import com.simbirsoft.kondratyev.ruslan.pizzeria.repository.Kitchen;

import java.util.Collection;
import java.util.Scanner;
import java.lang.NumberFormatException;

import static com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Suggest.SIZE;
import static com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs.WRONG_INPUT;

public class Dialog{
    private Scanner scanner = new Scanner(System.in);
    public static final Integer ABORT = 0xFF;

    public void wish() {
        System.out.print("Пожалуйста, отметьте необходимые пункты меню (от 4 до 7 ингридиентов, но не более" + Kitchen.maxPortionPizza + " порций на пиццу), (N) отмена заказа...\n");
    }
    public Integer suggest(Suggest suggest, Pair<Ingredient,Integer> pair) {
        if (suggest == SIZE) {
            System.out.print("укажите размер пиццы: 25, 35 либо 45 см (введите цифры), отмена (N): ");
        }else if (pair != null) {
            System.out.print(pair.getFirst().getName() + "(" + pair.getSecond() + " на складе " + ", "+ Kitchen.maxPortionIngredient + " на порции): ");
        }
        String answer = scanner.nextLine();
        while(true) {
            if (!answer.isEmpty() || suggest == SIZE) {
                if (!(suggest == SIZE && answer.matches("25|35|45")) && !(suggest != SIZE && answer.matches("\\d{1,}"))){
                    if (!answer.toUpperCase().matches("N")) {
                        this.wrongMessage(WRONG_INPUT);
                        answer = scanner.nextLine();
                        continue;
                    } else {
                        return this.ABORT;
                    }
                }
            }
            break;
        }
        try {
            return Integer.parseInt(answer,10);
        } catch(NumberFormatException err) {
            return 0;
        }
    }
    public boolean welcome() {
        System.out.print("<<Добро пожаловать в нашей пиццерии SimbirSoft>>\nЖелаете заказать пиццу?(Y/N): ");
        String answer;
        while(true) {
            answer = scanner.nextLine();
            if(answer.toUpperCase().matches("[YN]{1}")) {
                break;
            } else {
                System.out.println("Введите Y для подтверждения, либо N для отмены");
            }
        }
        return answer.toUpperCase().matches("Y");
    }
    public void issueResult(Collection<String> pizzaDescription){
        for(int i = 0; i < 10; i++) {
            try {
                System.out.print(". ");
                Thread.sleep(500);
            }
            catch(InterruptedException err) {
                throw new RuntimeException(err.getMessage(), err.getCause());
            }
        }
        System.out.println("\nВаша пицца готова:");
        for(String data : pizzaDescription){
            System.out.println(data);
        }
        System.out.println("Приятного аппетита");
    }
    public boolean repeat(){
        boolean result;
        System.out.print("Желаете повторить заказ?(Y/N)");
        while(true) {
            String answer = scanner.nextLine();
            if (!answer.toUpperCase().matches("[YN]{1}")) {
                System.out.print("Введите Y для заказа, N для завершения диалога");
                continue;
            } else {
                result = answer.toUpperCase().equals("Y");
                break;
            }
        }
        return result;
    }
    public void goodbuy(){
            System.out.println(".\n.\n.\nСпасибо, что посетили пиццерию SimbirSoft.");
    }
    public void wrongMessage(Wrongs typeWrong) {
        switch (typeWrong){
            case WRONG_INPUT:
                System.out.print("Введите целое число в допустимых пределах, пропустите компонент, либо N для отмены.\n");
                break;
            case WRONG_FORMATION:
                System.out.print("Превышено количество порций на пиццу, измените последние данные, либо N для отмены.\n");
                break;
                default:
        }
    }
    public void orderFormed() {
        System.out.print("Ваш заказ сформирован, ожидайте!\n");
    }
}
