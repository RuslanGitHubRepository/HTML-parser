package com.simbirsoft.kondratyev.ruslan.pizzeria.repository;

import com.simbirsoft.kondratyev.ruslan.pizzeria.interfacies.Store;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Ingredient;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs;
import com.simbirsoft.kondratyev.ruslan.pizzeria.views.Dialog;

import java.sql.*;
import java.util.*;

import static com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs.*;

public class StoreHouse_DB implements Store<Ingredient> {
    private final Map<Ingredient, Integer> historyStorage = new HashMap<>();
    private Properties connectionProperty = new Properties();


    private void creatDataBase(final Collection<String> types, final Collection<Integer> quantity) throws Exception {
        try (Connection connect2DB = DriverManager.getConnection("jdbc:mysql://ruslan:master@localhost:3306/dbIngredientstorehouse", connectionProperty);
            Statement statementDB = connect2DB.createStatement()) {
            connect2DB.setAutoCommit(false);
            statementDB.addBatch("DROP TABLE IF EXISTS Ingredients");
            statementDB.addBatch("CREATE TABLE IF NOT EXISTS Ingredients (id MEDIUMINT NOT NULL AUTO_INCREMENT, typeof VARCHAR(30) NOT NULL, storeCount MEDIUMINT, PRIMARY KEY (id));");
            Iterator<Integer> quantityIter = quantity.iterator();
            for (String type : types) {
                statementDB.addBatch("INSERT INTO dbingredientstorehouse.Ingredients(typeof, storeCount) VALUES ('" + type + "', " + quantityIter.next() + ")");
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

    public StoreHouse_DB(final Collection<String> types, final Collection<Integer> quantity) throws Exception {
        connectionProperty.setProperty("useSSL", "false");
        connectionProperty.setProperty("serverTimezone", "Europe/Moscow");
        Class.forName("com.mysql.cj.jdbc.Driver");
        creatDataBase(types, quantity);
    }

    public Wrongs getIngredient(final Ingredient type, final Integer quantity) throws Exception {
        if (quantity == 0) {
            return WRONG_NONE;
        }
        if (quantity == Dialog.ABORT) {
            return WRONG_WASHOUT;
        }
        try (Connection connect2DB = DriverManager.getConnection("jdbc:mysql://ruslan:master@localhost:3306/dbIngredientstorehouse", connectionProperty);
             Statement statementDB = connect2DB.createStatement()) {
            ResultSet requestResult = statementDB.executeQuery("SELECT storeCount FROM Ingredients WHERE typeof = '" + type.getName() + "';");
            requestResult.next();
            Integer countIngredientDB = requestResult.getInt("storeCount");
            if ((countIngredientDB >= quantity) && (countIngredientDB > 0)) {
                statementDB.execute("UPDATE Ingredients SET storeCount = " + (countIngredientDB - quantity) + " WHERE typeof ='" + type.getName() + "'");
                historyStorage.put(type, countIngredientDB - quantity);
                return WRONG_NONE;
            } else {
                return WRONG_INPUT;
            }
        } catch (SQLException Err) {
            throw Err;
        }
    }

    public Integer getQuantity(final Ingredient type) throws Exception {
        try (Connection connect2DB = DriverManager.getConnection("jdbc:mysql://ruslan:master@localhost:3306/dbIngredientstorehouse", connectionProperty);
             Statement statementDB = connect2DB.createStatement()) {
            ResultSet requestResult = statementDB.executeQuery("SELECT storeCount FROM Ingredients WHERE typeof = '" + type.getName() + "';");
            requestResult.next();
            return requestResult.getInt("storeCount");
        } catch (SQLException Err) {
            throw Err;
        }
    }

    public Collection<Ingredient> getAllIngredients()  throws Exception{
        Collection<Ingredient> listIndredients = new LinkedList<>();
        try (Connection connect2DB = DriverManager.getConnection("jdbc:mysql://ruslan:master@localhost:3306/dbIngredientstorehouse", connectionProperty);
             Statement statementDB = connect2DB.createStatement()) {
            ResultSet requestResult = statementDB.executeQuery("SELECT typeof FROM Ingredients;");
            while (requestResult.next()) {
                ((LinkedList<Ingredient>) listIndredients).addLast(new Ingredient(requestResult.getString("typeof")));
            }
            return listIndredients;
        } catch (SQLException Err) {
            throw Err;
        }
    }

    public void commitStore() {
        for (Map.Entry<Ingredient, Integer> ingredients : historyStorage.entrySet()) {
            historyStorage.replace(ingredients.getKey(), ingredients.getValue(), 0);
        }
    }

    public void restoreStore() throws Exception{
        try (Connection connect2DB = DriverManager.getConnection("jdbc:mysql://ruslan:master@localhost:3306/dbIngredientstorehouse", connectionProperty);
            Statement statementDB = connect2DB.createStatement()) {
            connect2DB.setAutoCommit(false);
            for(Map.Entry<Ingredient, Integer> ingredients : historyStorage.entrySet()) {
                statementDB.addBatch("UPDATE Ingredients SET storeCount = " + ingredients.getValue() + "WHERE typeof = '" + ingredients.getKey() + "'");
            }
            if (statementDB.executeBatch().length == historyStorage.size()) {
                connect2DB.commit();
            } else {
                connect2DB.rollback();
            }
        }catch (SQLException Err) {
            throw Err;
        }
    }

}