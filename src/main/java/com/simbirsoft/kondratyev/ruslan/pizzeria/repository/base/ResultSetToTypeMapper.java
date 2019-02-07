package com.simbirsoft.kondratyev.ruslan.pizzeria.repository.base;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetToTypeMapper<T> {
    T toValue(ResultSet rs) throws SQLException;
}
