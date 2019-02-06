package com.simbirsoft.kondratyev.ruslan.pizzeria.models;

import java.sql.*;

public class Connection2BD{
    private Connection connect;

    private String connection2DataBase;
    private Integer countConnections = 0;

    public static Integer COMMIT_OPERATION = 0;
    public static Integer POLLBACK_OPERATION = 1;

    private void creatConnect(boolean setAutoCommit) throws SQLException {
        connect = ConnectionSingltone.getObject().getConnection();
        connect.setAutoCommit(setAutoCommit);
    }
    public Connection getConnect(boolean setAutoCommit) throws SQLException {
        if(connect == null || connect.isClosed()) {
            creatConnect(setAutoCommit);
        }
        countConnections++;
        return connect;
    }
    public void commitOrRollBack(Integer typeOperation) throws SQLException{
        if(typeOperation.equals(COMMIT_OPERATION)){
            connect.commit();
        }
        if(typeOperation.equals(POLLBACK_OPERATION)){
            connect.rollback();
        }
    }
    public void closeDB() throws SQLException {
        if(--countConnections == 0) {
            connect.close();
        }
    }
}