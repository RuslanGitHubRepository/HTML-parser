package com.simbirsoft.kondratyev.ruslan.pizzeria.models;
import com.simbirsoft.kondratyev.ruslan.pizzeria.interfacies.Connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.*;

public class ConnectionSingltone {
    private Connections connection;
    private static ConnectionSingltone connectionSingltone;
    private ConnectionSingltone(){}
    public static void initialisationObject(Connections connection){
        if(connectionSingltone == null){
            connectionSingltone = new ConnectionSingltone();
        }
        connectionSingltone.connection = connection;
    }
    public static ConnectionSingltone getObject() {
        if(connectionSingltone == null){
            throw new MakerException("Error.Connection did't initialized.");
        }
        return connectionSingltone;
    }
    public Connection getConnection(){
        return connection.getConnection();
    }
}

