package com.simbirsoft.kondratyev.ruslan.pizzeria.service;

import com.simbirsoft.kondratyev.ruslan.pizzeria.interfacies.Kitchens;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Connection2BD;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Ingredient;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.MakerException;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.propertySingltone;
import com.simbirsoft.kondratyev.ruslan.pizzeria.views.Dialog;

import java.sql.*;
import java.util.*;

import static com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs.*;
import static com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs.WRONG_FORMATION;

public class Kitchen implements Kitchens<Ingredient> {
    private Integer sizePizza = 0;
    private Integer typeOfPizza = 0;
    private Integer currentPortion = 0;
    private final Connection2BD connectBD;
    private Collection<String> typesIngredient;
    public static boolean readinessFlag = false;
    public static Integer maxPortionPizza = 0;
    public static Integer maxPortionIngredient = 0;

    private void creatDataBase(final Collection<String> types) {
        try {
            Connection connect = connectBD.getConnect(false);
            Statement statement = connect.createStatement();
            statement.addBatch("DROP TABLE IF EXISTS Recipes");
            statement.addBatch("CREATE TABLE IF NOT EXISTS Recipes (id MEDIUMINT NOT NULL, PRIMARY KEY (id))");
            for (String type : types) {
                statement.addBatch("ALTER TABLE Recipes ADD "+type+" VARCHAR(30)");
            }
            if (statement.executeBatch().length == 2 + types.size()) {
                connectBD.commitRollBack("commit");
            } else {
                connectBD.commitRollBack("rollback");
            }
            connectBD.closeDB();
        } catch (SQLException err) {
            throw new MakerException("Kitchen.creatDataBase(): " + err.getSQLState(), err.getCause());
        }
    }

    public Kitchen(final Collection<String> types, final Integer maxIngredient, final Integer maxPizza) {
        maxPortionIngredient = maxIngredient;
        maxPortionPizza = maxPizza;
        typeOfPizza = 1;
        try {
            connectBD = new Connection2BD();
        }
        catch (ClassNotFoundException err) {
            throw new MakerException("KitchenConstructer(): " + err.getMessage(), err.getCause());
        }
        typesIngredient = types;
        creatDataBase(types);
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

        try{
            PreparedStatement statementDB = connectBD.getConnect(true).prepareStatement("UPDATE Recipes SET "+ingredient.getName()+" = ? WHERE id = " + typeOfPizza);
            statementDB.setInt(1, countToAdd);
            statementDB.execute();
            connectBD.closeDB();

        }catch (SQLException err) {
            throw new MakerException("Kitchen.addToRecipe(): " + err.getSQLState(), err.getCause());
        }

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

            try {
                Statement statementDB = connectBD.getConnect(true).createStatement();
                ResultSet resultSet = statementDB.executeQuery("SELECT * FROM Recipes WHERE id = " + typeOfPizza);
                resultSet.next();
                for (String str : typesIngredient){
                    if(resultSet.getInt(str) != 0) {
                        pizza.add(str + "->" + resultSet.getInt(str) + " пр.");
                    }
                }
                connectBD.closeDB();
            } catch (SQLException err) {
                throw new MakerException("Kn.getPizza(): " + err.getSQLState(), err.getCause());
            }

        readinessFlag = true;
        typeOfPizza++;
        return pizza;
    }

    public void restartKitchen() {
        sizePizza = 0;
        currentPortion = 0;
        readinessFlag = false;
        try {
            Statement statementDB = connectBD.getConnect(true).createStatement();
             statementDB.execute("INSERT INTO Recipes(id) VALUES ("+typeOfPizza+")");
             connectBD.closeDB();
        }catch (SQLException err) {
            throw new MakerException("Kn.restartKitchen(): " + err.getSQLState(), err.getCause());
        }
    }
}
