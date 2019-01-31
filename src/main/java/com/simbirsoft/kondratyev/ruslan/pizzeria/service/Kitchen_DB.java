package com.simbirsoft.kondratyev.ruslan.pizzeria.service;

import com.simbirsoft.kondratyev.ruslan.pizzeria.interfacies.Kitchens;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Ingredient;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs;
import com.simbirsoft.kondratyev.ruslan.pizzeria.views.Dialog;

import java.sql.*;
import java.util.*;

import static com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs.*;
import static com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs.WRONG_FORMATION;

public class Kitchen_DB extends Kitchens<Ingredient> {
    private Integer sizePizza = 0;
    private Integer typeOfPizza = 0;
    private Integer currentPortion = 0;
    private Properties connectionProperty = new Properties();
    private Collection<String> typesIngredient;

    private void creatDataBase(final Collection<String> types) throws Exception {
        try (Connection connect2DB = DriverManager.getConnection("jdbc:mysql://ruslan:master@localhost:3306/dbIngredientstorehouse", connectionProperty);
            Statement statementDB = connect2DB.createStatement()) {
            connect2DB.setAutoCommit(false);
            statementDB.addBatch("DROP TABLE IF EXISTS Recipes");
            statementDB.addBatch("CREATE TABLE IF NOT EXISTS Recipes (id MEDIUMINT NOT NULL, PRIMARY KEY (id));");

            for (String type : types) {
                statementDB.addBatch("ALTER TABLE Recipes ADD " + type +" VARCHAR(30)");
            }

            if (statementDB.executeBatch().length == types.size() + 2) {
                connect2DB.commit();
            } else {
                connect2DB.rollback();
            }
        } catch (SQLException Err) {
            throw Err;
        }
    }

    public Kitchen_DB(final Collection<String> types, Integer maxIngredient, Integer maxPizza) throws Exception {
        maxPortionIngredient = maxIngredient;
        maxPortionPizza = maxPizza;
        typeOfPizza = 1;
        connectionProperty.setProperty("useSSL", "false");
        connectionProperty.setProperty("serverTimezone", "Europe/Moscow");
        Class.forName("com.mysql.cj.jdbc.Driver");
        typesIngredient = types;
        creatDataBase(types);

    }

    public Wrongs addToRecipe(Ingredient ingredient, Integer countToAdd) throws Exception  {

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

        try (Connection connect2DB = DriverManager.getConnection("jdbc:mysql://ruslan:master@localhost:3306/dbIngredientstorehouse", connectionProperty);
             Statement statementDB = connect2DB.createStatement()) {

            statementDB.execute("UPDATE Recipes SET " + ingredient.getName()+ " = '" + countToAdd + "' WHERE id = " + typeOfPizza);

        }catch (SQLException Err) {
            throw Err;
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
    public Collection<String> getPizza() throws Exception{
        List<String> pizza = new ArrayList<>();
        pizza.add("Размер пиццы: " + sizePizza);

            try (Connection connect2DB = DriverManager.getConnection("jdbc:mysql://ruslan:master@localhost:3306/dbIngredientstorehouse", connectionProperty);
                 Statement statementDB = connect2DB.createStatement()) {

                ResultSet resultSet = statementDB.executeQuery("SELECT * FROM Recipes WHERE id = " + typeOfPizza);
                resultSet.next();
                for (String str : typesIngredient){
                    if(resultSet.getInt(str) != 0) {
                        pizza.add(str + "->" + resultSet.getInt(str) + " пр.");
                    }
                }

            } catch (SQLException Err) {
                throw Err;
            }

        readinessFlag = true;
        typeOfPizza++;
        return pizza;
    }
    public void restartKitchen() throws Exception{
        sizePizza = 0;
        currentPortion = 0;
        readinessFlag = false;
        try (Connection connect2DB = DriverManager.getConnection("jdbc:mysql://ruslan:master@localhost:3306/dbIngredientstorehouse", connectionProperty);
             Statement statementDB = connect2DB.createStatement()) {
             statementDB.execute("INSERT INTO Recipes(id) VALUES ("+typeOfPizza+")");
        }catch (SQLException Err) {
            throw Err;
        }
    }
}
