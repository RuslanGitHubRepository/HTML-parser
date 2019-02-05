package com.simbirsoft.kondratyev.ruslan.pizzeria.models;

import java.sql.*;

public class Connection2BD{
    private Connection connect;

    private String connection2DataBase;
    private Integer countConnrctions = 0;
    private void connect2DB(boolean setAutoCommit) throws SQLException {
            connect = DriverManager.getConnection(connection2DataBase, propertySingltone.getPropertyDataBase());
            if (setAutoCommit == false) {
                connect.setAutoCommit(false);
            }
    }

    public Connection2BD() throws ClassNotFoundException {
        connection2DataBase = "jdbc:"+
                propertySingltone.getProperty("dbtype")+
                "://"+propertySingltone.getProperty("dblogin")+
                ":"+propertySingltone.getProperty("dbpassword")+
                "@"+propertySingltone.getProperty("dbbase")+
                ":"+propertySingltone.getProperty("dbport")+
                "/"+propertySingltone.getProperty("dbname");
        Class.forName(propertySingltone.getProperty("dbdriver"));
    }

    public Connection getConnect(boolean setAutoCommit) throws SQLException {
        if(connect == null || connect.isClosed()) {
          connect2DB(setAutoCommit);
        }
        countConnrctions++;
        return connect;
    }

    public void commitRollBack(String type) throws SQLException{
        if(type.matches("commit")){
            connect.commit();
        }
        if(type.matches("rollback")){
            connect.rollback();
        }
    }

    public void closeDB() throws SQLException {
        if(--countConnrctions == 0) {
            connect.close();
        }
    }
}