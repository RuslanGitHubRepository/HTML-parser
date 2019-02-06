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

public class Kitchen implements Kitchens<Ingredient> {
    private Integer sizePizza = 0;
    private Integer typeOfPizza = 0;
    private Integer currentPortion = 0;
    private final Connection2BD connectBD;
    private Collection<String> typesIngredient;
    public static boolean readinessFlag = false;
    public static Integer maxPortionPizza = 0;
    public static Integer maxPortionIngredient = 0;

    @Deprecated //алгоритм создания БД вынесен во внешний DDL-script
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
                connectBD.commitOrRollBack(Connection2BD.COMMIT_OPERATION);
            } else {
                connectBD.commitOrRollBack(Connection2BD.POLLBACK_OPERATION);
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
        connectBD = new Connection2BD();
        typesIngredient = types;
        /*creatDataBase(types);*/
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
            PreparedStatement statementDB = connectBD.getConnect(true).prepareStatement(DbQueryConstants.getUpdateCommand(ingredient.getName()));
            statementDB.setInt(1, countToAdd);
            statementDB.setInt(2, typeOfPizza);
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
                Connection connect = connectBD.getConnect(true);
                PreparedStatement statementPrep = connect.prepareStatement(DbQueryConstants.GET_RECIPE_KITCHEN);
                statementPrep.setInt(1,typeOfPizza);
                ResultSet resultSet = statementPrep.executeQuery();
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
            Connection connect = connectBD.getConnect(true);
            PreparedStatement statementPrep = connect.prepareStatement(DbQueryConstants.INSERT_NEW_RECORD_KITCHEN);
            statementPrep.setInt(1,typeOfPizza);
            statementPrep.execute();
            connectBD.closeDB();
        }catch (SQLException err) {
            throw new MakerException("Kn.restartKitchen(): " + err.getSQLState(), err.getCause());
        }
    }
}
