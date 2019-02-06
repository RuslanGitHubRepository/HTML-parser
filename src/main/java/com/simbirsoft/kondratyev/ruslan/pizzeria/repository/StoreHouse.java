package com.simbirsoft.kondratyev.ruslan.pizzeria.repository;

import com.simbirsoft.kondratyev.ruslan.pizzeria.interfacies.Store;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Connection2BD;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Ingredient;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.MakerException;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs;
import com.simbirsoft.kondratyev.ruslan.pizzeria.views.Dialog;

import java.sql.*;
import java.util.*;

import static com.simbirsoft.kondratyev.ruslan.pizzeria.models.enums.Wrongs.*;

abstract class DbQueryConstants {
    public static final String GET_QUANTITY = "SELECT storeCount FROM Ingredients WHERE typeof = ?";
    public static final String GET_ALL_QUANTITY = "SELECT typeof FROM Ingredients";
    public static final String UPDATE_COUNTS = "UPDATE Ingredients SET storeCount = ? WHERE typeof = ?";
}

public class StoreHouse extends DbQueryConstants implements Store<Ingredient> {
    private final Connection2BD connectBD;

    @Deprecated //алгоритм создания БД вынесен во внешний DDL-script
    private void creatDataBase(final Collection<String> types, final Collection<Integer> quantity) {

        try {
            Connection connect = connectBD.getConnect(false);
            Statement statement = connect.createStatement();
            statement.addBatch("DROP TABLE IF EXISTS Ingredients");
            statement.addBatch("CREATE TABLE IF NOT EXISTS Ingredients (id MEDIUMINT NOT NULL AUTO_INCREMENT, typeof VARCHAR(30) NOT NULL, storeCount MEDIUMINT, PRIMARY KEY (id));");
            PreparedStatement statementPrep = connect.prepareStatement("INSERT INTO Ingredients(typeof, storeCount) VALUES (?, ?)");
            Iterator<Integer> quantityIter = quantity.iterator();
            for (String type : types) {
                statementPrep.setString(1, type);
                statementPrep.setInt(2, quantityIter.next());
                statementPrep.addBatch();
            }
            if (statement.executeBatch().length == 2 && statementPrep.executeBatch().length == types.size()) {
                connectBD.commitRollBack("commit");
            } else {
                connectBD.commitRollBack("rollback");
            }
            connectBD.closeDB();
        } catch (SQLException err) {
            throw new MakerException("SH.creatDataBase(): " + err.getSQLState(), err.getCause());
        }
    }

    public StoreHouse(final Collection<String> types, final Collection<Integer> quantity){
        try {
            connectBD = new Connection2BD();
        }
        catch (ClassNotFoundException err) {
            throw new MakerException("StoreHouseConstructer(): " + err.getMessage(), err.getCause());
        }
        /*creatDataBase(types, quantity);*/
    }

    public Wrongs getIngredient(final Ingredient type, final Integer quantity) {
        if (quantity == 0) {
            return WRONG_NONE;
        }
        if (quantity == Dialog.ABORT) {
            return WRONG_WASHOUT;
        }
        Integer countIngredientDB = getQuantity(type);
        if ((countIngredientDB < quantity) || (countIngredientDB == 0)) {
            return WRONG_INPUT;
        }
        return WRONG_NONE;
    }

    public Integer getQuantity(final Ingredient type){
        try {
            Connection connect = connectBD.getConnect(true);
            PreparedStatement statementPrep = connect.prepareStatement(GET_QUANTITY);
            statementPrep.setString(1,type.getName());
            ResultSet requestResult = statementPrep.executeQuery();
            requestResult.next();
            Integer count = requestResult.getInt("storeCount");
            connectBD.closeDB();
            return count;
        } catch (SQLException err) {
            throw new MakerException("SH.getQuantity(): " + err.getMessage(), err.getCause());
        }
    }

    public Collection<Ingredient> getAllIngredients() {
        Collection<Ingredient> listIndredients = new LinkedList<>();
        try {
            Connection connect = connectBD.getConnect(true);
            Statement statementDB = connect.createStatement();
            ResultSet requestResult = statementDB.executeQuery(GET_ALL_QUANTITY);
            while (requestResult.next()) {
                ((LinkedList<Ingredient>) listIndredients).addLast(new Ingredient(requestResult.getString("typeof")));
            }
            connectBD.closeDB();
            return listIndredients;
        } catch (SQLException err) {
            throw new MakerException("SH.getAllIngredients(): " + err.getMessage(), err.getCause());
        }
    }

    public void commitStore(Map<Ingredient,Integer> ingrediens) {
        if(ingrediens.size() == 0){
            return;
        }
        Integer countIngredient;
        try{
            Connection connect = connectBD.getConnect(false);
            PreparedStatement statementPrep = connect.prepareStatement(UPDATE_COUNTS);
            for (Map.Entry<Ingredient,Integer> type : ingrediens.entrySet()) {
                countIngredient = getQuantity(type.getKey());
                statementPrep.setInt(1, countIngredient - type.getValue());
                statementPrep.setString(2, type.getKey().getName());
                statementPrep.addBatch();
            }
            if (statementPrep.executeBatch().length == ingrediens.size()) {
                connectBD.commitRollBack("commit");
            } else {
                connectBD.commitRollBack("rollback");
            }
            connectBD.closeDB();
        } catch (SQLException err) {
            throw new MakerException("SH.commitStore(): " + err.getSQLState(), err.getCause());
        }
    }
}