package com.simbirsoft.kondratyev.ruslan.pizzeria.models;

import com.simbirsoft.kondratyev.ruslan.pizzeria.interfacies.Connections;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionPropertyFile implements Connections {
    private final Properties propertyDataBase = new Properties();
    private static final String SEPARATOR = "?";
    public static final String TYPE_API_JDBC = "jdbc:"+SEPARATOR+"://"+SEPARATOR+":"+SEPARATOR+"@"+SEPARATOR+":"+SEPARATOR+"/"+SEPARATOR;
    public static final String DB_TYPE = "dbtype";
    public static final String DB_LOGIN = "dblogin";
    public static final String DB_PASS = "dbpassword";
    public static final String DB_BASE = "dbbase";
    public static final String DB_PORT = "dbport";
    public static final String DB_NAME = "dbname";
    public static final String DB_DRIVER = "dbdriver";

    private String url;
    public ConnectionPropertyFile(final String fileName)
    {
        try {
            InputStream configserverStream = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName);
            propertyDataBase.load(configserverStream);
            Class.forName(propertyDataBase.getProperty(DB_DRIVER));
        }catch(IOException | ClassNotFoundException err){
           new MakerException(err.getMessage(),err.getCause());
    }
    }
    public void getURLBase()
    {
        StringBuilder str = new StringBuilder(TYPE_API_JDBC);

        int index = str.indexOf(SEPARATOR);
        str = str.deleteCharAt(index);
        str.insert(index, propertyDataBase.getProperty(DB_TYPE));
        index = str.indexOf(SEPARATOR);
        str = str.deleteCharAt(index);
        str.insert(index, propertyDataBase.getProperty(DB_LOGIN));
        index = str.indexOf(SEPARATOR);
        str = str.deleteCharAt(index);
        str.insert(index, propertyDataBase.getProperty(DB_PASS));
        index = str.indexOf(SEPARATOR);
        str = str.deleteCharAt(index);
        str.insert(index, propertyDataBase.getProperty(DB_BASE));
        index = str.indexOf(SEPARATOR);
        str = str.deleteCharAt(index);
        str.insert(index, propertyDataBase.getProperty(DB_PORT));
        index = str.indexOf(SEPARATOR);
        str = str.deleteCharAt(index);
        str.insert(index, propertyDataBase.getProperty(DB_NAME));
        url = str.toString();
    }
    public Connection getConnection(){
        if(url == null) {
            getURLBase();
        }
        try {
            return DriverManager.getConnection(url, propertyDataBase);
        }
        catch(SQLException err){
            throw new MakerException(err.getMessage(),err.getCause());
        }
    }
}
