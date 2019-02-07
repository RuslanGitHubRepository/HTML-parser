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

class DataBaseInterplayStore{
    private final Connection2BD connectBD;

    DataBaseInterplayStore(){
        connectBD = new Connection2BD();
    }
    Integer getIngredientQuantity(final String nameComponent){
        try {
            Connection connect = connectBD.getConnect(true);
            PreparedStatement statementPrep = connect.prepareStatement(DbQueryConstants.GET_QUANTITY_STORE);
            statementPrep.setString(1, nameComponent);
            ResultSet requestResult = statementPrep.executeQuery();
            requestResult.next();
            Integer count = requestResult.getInt("storeCount");
            connectBD.closeDB();
            return count;
        } catch (SQLException err) {
            throw new MakerException("SH.getQuantity(): " + err.getMessage(), err.getCause());
        }
    }
    List<String> getListAllNames(){
        List<String> listNames = new ArrayList<>();
    try {
        Connection connect = connectBD.getConnect(true);
        Statement statementDB = connect.createStatement();
        ResultSet requestResult = statementDB.executeQuery(DbQueryConstants.GET_ALL_QUANTITY_STORE);
        while (requestResult.next()) {
            listNames.add(requestResult.getString("typeof"));
        }
        connectBD.closeDB();
        return listNames;
    } catch (SQLException err) {
        throw new MakerException("SH.getAllIngredients(): " + err.getMessage(), err.getCause());
    }
    }
    void withdrawPosition(Map<Ingredient,Integer> ingrediens){
        Integer countIngredient;
        try{
            Connection connect = connectBD.getConnect(false);
            PreparedStatement statementPrep = connect.prepareStatement(DbQueryConstants.UPDATE_COUNTS_STORE);

            for (Map.Entry<Ingredient,Integer> type : ingrediens.entrySet()) {
                countIngredient = getIngredientQuantity(type.getKey().getName());
                statementPrep.setInt(1, countIngredient - type.getValue());
                statementPrep.setString(2, type.getKey().getName());
                statementPrep.addBatch();
            }
            if (statementPrep.executeBatch().length == ingrediens.size()) {
                connectBD.commitOrRollBack(Connection2BD.COMMIT_OPERATION);
            } else {
                connectBD.commitOrRollBack(Connection2BD.COMMIT_OPERATION);
            }
            connectBD.closeDB();
        } catch (SQLException err) {
            throw new MakerException("SH.commitStore(): " + err.getSQLState(), err.getCause());
        }
    }
}
public class StoreHouse  implements Store<Ingredient> {

    private DataBaseInterplayStore dataBaseInterplay = new DataBaseInterplayStore();

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
       return dataBaseInterplay.getIngredientQuantity(type.getName());
    }

    public Collection<Ingredient> getAllIngredients() {
        Collection<Ingredient> listIndredients = new LinkedList<>();
        List<String> listName = dataBaseInterplay.getListAllNames();
        for(String name:listName){
            ((LinkedList<Ingredient>) listIndredients).addLast(new Ingredient(name));
        }
        return listIndredients;
    }

    public void commitStore(Map<Ingredient,Integer> ingrediens) {
        if(ingrediens.size() == 0){
            return;
        }
        dataBaseInterplay.withdrawPosition(ingrediens);
    }
}