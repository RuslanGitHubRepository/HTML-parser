package com.simbirsoft.kondratyev.ruslan.pizzeria.repository.base;

import com.simbirsoft.kondratyev.ruslan.pizzeria.models.Connection2BD;
import com.simbirsoft.kondratyev.ruslan.pizzeria.models.MakerException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class BaseRepository {
    private final Connection2BD connectBD = new Connection2BD();

    private PreparedStatement getStatement(String query, List<Object> parameters) throws SQLException {
        Connection connect = connectBD.getConnect(true);
        PreparedStatement statement = connect.prepareStatement(query);
        for (int i = 0; i < parameters.size(); i++) {
            Object parameter = parameters.get(i);

            if (parameter instanceof String) {
                statement.setString(i+1, (String) parameter);
            } else if (parameter instanceof Integer) {
                statement.setInt(i+1, (Integer) parameter);
            }
            //TODO если нужно - добавить дальнейшие типы
        }

        return statement;
    }

    protected <T> T executeQuery(String query, List<Object> parameters, ResultSetToTypeMapper<T> mapper) {
        try {
            PreparedStatement statement = getStatement(query, parameters);

            ResultSet rs = statement.executeQuery();
            T t = mapper.toValue(rs);
            return t;
        } catch (SQLException err) {
            throw new MakerException("exception with process sql query: " + err.getMessage(), err.getCause());
        }
    }
}
