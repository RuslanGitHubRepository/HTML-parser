package com.simbirsoft.kondratyev.ruslan.pizzeria.repository.ingredient;

import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Ingredient;
import com.simbirsoft.kondratyev.ruslan.pizzeria.repository.base.ResultSetToTypeMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IngredientResultSetMapper implements ResultSetToTypeMapper<Ingredient> {

    @Override
    public Ingredient toValue(ResultSet rs) throws SQLException {
        Ingredient item = new Ingredient();
        item.setName(rs.getString("typeof"));
        item.setStoreCount(rs.getInt("storeCount"));
        return item;
    }
}
