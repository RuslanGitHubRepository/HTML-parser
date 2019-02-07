package com.simbirsoft.kondratyev.ruslan.pizzeria.repository;

import com.simbirsoft.kondratyev.ruslan.pizzeria.interfacies.Kitchens;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Connection2BD;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Ingredient;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.MakerException;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs;
import com.simbirsoft.kondratyev.ruslan.pizzeria.views.Dialog;

import java.sql.*;
import java.util.*;

import static com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs.*;
import static com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs.WRONG_FORMATION;
class DataBaseInterplayKitchen {
    private final Connection2BD connectBD;
    DataBaseInterplayKitchen(){
        connectBD = new Connection2BD();
    }
    void addToRecipe(String nameIngredient, Integer countIngredient){
    try {
        Connection connect = connectBD.getConnect(true);
        PreparedStatement statementPrep = connect.prepareStatement(DbQueryConstants.INSERT_NEW_RECORD_KITCHEN);
        statementPrep.setString(1, nameIngredient);
        statementPrep.setInt(2, countIngredient);
        statementPrep.execute();
        connectBD.closeDB();
    }catch (SQLException err) {
        throw new MakerException("Kn.restartKitchen(): " + err.getSQLState(), err.getCause());
    }
    }
    List<String> getFinaliseRecipe(){
        List<String> order = new ArrayList<>();
        try {
            Connection connect = connectBD.getConnect(true);
            Statement statement = connect.createStatement();
            ResultSet resultSet = statement.executeQuery(DbQueryConstants.GET_RECIPE_KITCHEN);
            while(resultSet.next()) {
                order.add(resultSet.getString("name_ingredient") + "->" + resultSet.getInt("count_ingredient") + " пр.");
            }
            connectBD.closeDB();
        } catch (SQLException err) {
            throw new MakerException("Kn.getPizza(): " + err.getSQLState(), err.getCause());
        }
        return order;
    }
    void clearOrder(){
    try {
        Connection connect = connectBD.getConnect(true);
        Statement statement = connect.createStatement();
        statement.execute(DbQueryConstants.CLEAR_RECIPE_KITCHEN);
        connectBD.closeDB();
    } catch (SQLException err) {
        throw new MakerException("Kn.getPizza(): " + err.getSQLState(), err.getCause());
    }
    }
}
public class Kitchen implements Kitchens<Ingredient> {
    private Integer sizePizza = 0;
    private Integer typeOfPizza = 0;
    private Integer currentPortion = 0;
    private final Connection2BD connectBD;
    public static boolean readinessFlag = false;
    public static Integer maxPortionPizza = 0;
    public static Integer maxPortionIngredient = 0;
    private DataBaseInterplayKitchen dataBaseInterplayKitchen = new DataBaseInterplayKitchen();

    public Kitchen(final Integer maxIngredient, final Integer maxPizza) {
        maxPortionIngredient = maxIngredient;
        maxPortionPizza = maxPizza;
        typeOfPizza = 1;
        connectBD = new Connection2BD();
    }
    public Wrongs addToRecipe(Ingredient ingredient, Integer countToAdd) {

        if (countToAdd == Dialog.ABORT){
            return WRONG_WASHOUT;
        }
        if (countToAdd > maxPortionIngredient){
            return WRONG_INPUT;
        }
        if ((countToAdd + currentPortion) > maxPortionPizza){
            return WRONG_FORMATION;
        }
        if (countToAdd == 0){
            return WRONG_NONE;
        }

        dataBaseInterplayKitchen.addToRecipe(ingredient.getName(),countToAdd);

        currentPortion += countToAdd;
        if (currentPortion.equals(maxPortionPizza)){
            readinessFlag = true;
        }
        return WRONG_NONE;
    }

    public Wrongs setSizePizza(Integer sizePizza) {
        if (sizePizza == Dialog.ABORT){
            return WRONG_WASHOUT;
        }
        this.sizePizza = sizePizza;
        return WRONG_NONE;
    }

    public Collection<String> getPizza() {
        List<String> pizza = new ArrayList<>();
        pizza.add("Размер пиццы: " + sizePizza);
        pizza.addAll(dataBaseInterplayKitchen.getFinaliseRecipe());
        readinessFlag = true;
        typeOfPizza++;
        return pizza;
    }

    public void restartKitchen() {
        sizePizza = 0;
        currentPortion = 0;
        readinessFlag = false;
        dataBaseInterplayKitchen.clearOrder();
    }
}
